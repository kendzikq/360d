package com.example._360d.service;

import com.example._360d.client.NBPClient;
import com.example._360d.model.AntiFraudStatus;
import com.example._360d.model.Currency;
import com.example._360d.model.NBPResponse;
import com.example._360d.repository.OrderRepository;
import com.example._360d.verification.AntiFraudVerificationChain;
import com.example._360d.verification.AverageRule;
import com.example._360d.verification.NigerianRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example._360d.model.AntiFraudStatus.ACCEPTED;
import static com.example._360d.model.AntiFraudStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class AntiFraudService {

    private static final HashSet<String> HANDLED_CURRENCIES = Arrays.stream(Currency.values())
            .map(Currency::getValue)
            .collect(Collectors.toCollection(HashSet::new));

    private final OrderRepository orderRepository;
    private final NBPClient nbpClient;
    private final EmailNotifier emailNotifier;

    private final TransactionTemplate transactionTemplate;


    public void monitor() {

        var nbpResponse = nbpClient.getRates();

        Map<Currency, Double> rates = Arrays.stream(nbpResponse.getContent()[0].getRates())
                .filter(rate -> HANDLED_CURRENCIES.contains(rate.getCode()))
                .collect(Collectors.toMap(rate -> Currency.valueOf(rate.getCode()), NBPResponse.Rate::getMid));
        rates.put(Currency.PLN, 1.0);

        long average = calculateAverage(rates);
        var pendingOrders = orderRepository.findAllByAntiFraudStatus(PENDING);

        if (pendingOrders.isEmpty()) {
            log.info("No pending orders - the job finish the job here");
            return;
        }

        var antiFraudRule = new NigerianRule();
        antiFraudRule.linkWith(new AverageRule(average, rates));
        var verificationChain = new AntiFraudVerificationChain(antiFraudRule);

        for (var order : pendingOrders) {

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                    if (verificationChain.isNotFraud(order)) {

                        orderRepository.changeStatus(AntiFraudStatus.ACCEPTED, order.getId());
                        emailNotifier.sendSuccessEmail(order);

                    } else {
                        orderRepository.changeStatus(AntiFraudStatus.REJECTED, order.getId());
                        emailNotifier.sendFailureEmail(order);
                    }
                }
            });
        }
    }

    public long calculateAverage(Map<Currency, Double> rates) {

        var orders = orderRepository.findAllByAntiFraudStatus(ACCEPTED);

        long sum = 0;
        long rows = orders.size();

        for (var order : orders) {

            Double rate = rates.get(order.getCurrency());
            sum += order.getAmount() * rate;

        }

        return sum / rows;
    }
}

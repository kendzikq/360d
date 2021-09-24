package com.example.ordersystem.service;

import com.example.ordersystem.antifraudrule.AntiFraudRule;
import com.example.ordersystem.model.AntiFraudStatus;
import com.example.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static com.example.ordersystem.model.AntiFraudStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class AntiFraudService {

    private final OrderRepository orderRepository;
    private final EmailNotifier emailNotifier;
    private final List<AntiFraudRule> antiFraudRules;

    private final TransactionTemplate transactionTemplate;


    public void monitor() {

        var pendingOrders = orderRepository.findAllByAntiFraudStatus(PENDING);

        if (pendingOrders.isEmpty()) {
            log.info("No pending orders - the job finishes work here");
            return;
        }


        for (var order : pendingOrders) {

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                    if (antiFraudRules.stream().noneMatch(rule -> rule.isFraud(order))) {

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
}

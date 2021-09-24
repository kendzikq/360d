package com.example.ordersystem.antifraudrule;

import com.example.ordersystem.client.NBPClientWrapper;
import com.example.ordersystem.repository.OrderRepository;
import com.example.ordersystem.repository.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.example.ordersystem.model.AntiFraudStatus.ACCEPTED;

/**
 * an order amount cannot exceed the average multiplied by the chosen factor
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AverageRule implements AntiFraudRule {

    private static final int MULTIPLIER = 5;

    private final AVGCalculator avgCalculator;
    private final NBPClientWrapper nbpClientWrapper;


    @Override
    public boolean isFraud(OrderEntity order) {

        var rates = nbpClientWrapper.getRates();
        Double rate = rates.get(order.getCurrency());

        double avgPLN = avgCalculator.calculateAVGInPLN();

        if (order.getAmount() * rate > MULTIPLIER * avgPLN) {
            log.warn("average rule breach for order: {}", order);
            return true;
        }

        return false;
    }

    @Component
    @RequiredArgsConstructor
    public static class AVGCalculator {

        private final OrderRepository orderRepository;
        private final NBPClientWrapper nbpClientWrapper;

        public double calculateAVGInPLN() {

            var rates = nbpClientWrapper.getRates();

            return orderRepository.findAllByAntiFraudStatus(ACCEPTED).stream()
                    .mapToDouble(o -> o.getAmount() * rates.get(o.getCurrency()))
                    .average()
                    .getAsDouble();
        }
    }
}

package com.example._360d.antifraudrule;

import com.example._360d.client.NBPClientWrapper;
import com.example._360d.repository.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NigerianRule implements AntiFraudRule {

    private static final String NIGERIA = "NIGERIA";
    private static final double MAX_AMOUNT_PLN = 100000.00;

    private final NBPClientWrapper nbpClientWrapper;

    @Override
    public boolean isFraud(OrderEntity order) {

        var rates = nbpClientWrapper.getRates();
        double orderAmountPLN = order.getAmount() * rates.get(order.getCurrency());

        if (NIGERIA.equals(order.getUserCountry()) && orderAmountPLN > MAX_AMOUNT_PLN) {
            log.warn("nigerian rule breach for order: {}", order);
            return true;
        }

        return false;
    }
}

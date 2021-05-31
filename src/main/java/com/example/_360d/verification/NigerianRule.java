package com.example._360d.verification;

import com.example._360d.repository.entity.OrderEntity;

public class NigerianRule extends AntiFraudRule {

    private static final String NIGERIA = "NIGERIA";

    @Override
    public boolean check(OrderEntity order) {

        if (NIGERIA.equals(order.getUserCountry()) && order.getAmount() > 100000) {
            return false;
        }

        return checkNext(order);
    }
}

package com.example._360d.verification;

import com.example._360d.model.Currency;
import com.example._360d.repository.entity.OrderEntity;

import java.util.Map;

public class AverageRule extends AntiFraudRule {

    private long average;

    private Map<Currency, Double> rates;

    public AverageRule(long average, Map<Currency, Double> rates) {
        this.average = average;
        this.rates = rates;
    }

    @Override
    public boolean check(OrderEntity order) {

        Double rate = rates.get(order.getCurrency());

        if (order.getAmount() * rate > 5 * average) {
            return false;
        }

        return checkNext(order);
    }
}

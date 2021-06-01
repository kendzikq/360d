package com.example._360d.verification;

import com.example._360d.model.Currency;
import com.example._360d.repository.entity.OrderEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
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
            log.warn("average rule breach for order: {}", order);
            return false;
        }

        return checkNext(order);
    }
}

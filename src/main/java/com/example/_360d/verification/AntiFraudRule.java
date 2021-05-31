package com.example._360d.verification;

import com.example._360d.repository.entity.OrderEntity;

public abstract class AntiFraudRule {

    private AntiFraudRule next;

    public AntiFraudRule linkWith(AntiFraudRule next) {
        this.next = next;
        return next;
    }

    public abstract boolean check(OrderEntity order);

    public boolean checkNext(OrderEntity order) {
        if (next == null) {
            return true;
        }
        return next.check(order);
    }
}

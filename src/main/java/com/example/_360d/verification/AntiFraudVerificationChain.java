package com.example._360d.verification;

import com.example._360d.repository.entity.OrderEntity;

public class AntiFraudVerificationChain {

    private AntiFraudRule antiFraudRule;

    public AntiFraudVerificationChain(AntiFraudRule antiFraudRule) {
        this.antiFraudRule = antiFraudRule;
    }

    public boolean isNotFraud(OrderEntity order) {
        return antiFraudRule.check(order);
    }
}

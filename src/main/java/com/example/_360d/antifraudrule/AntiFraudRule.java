package com.example._360d.antifraudrule;

import com.example._360d.repository.entity.OrderEntity;

public interface AntiFraudRule {

    boolean isFraud(OrderEntity order);

}

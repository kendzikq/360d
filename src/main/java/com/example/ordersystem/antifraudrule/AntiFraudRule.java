package com.example.ordersystem.antifraudrule;

import com.example.ordersystem.repository.entity.OrderEntity;

public interface AntiFraudRule {

    boolean isFraud(OrderEntity order);

}

package com.example.ordersystem.service;

import com.example.ordersystem.mapper.OrderMapper;
import com.example.ordersystem.model.OrderRequest;
import com.example.ordersystem.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.ordersystem.model.OrderRequestResponse.RegistrationStatus;
import static com.example.ordersystem.model.OrderRequestResponse.RegistrationStatus.REGISTERED;
import static com.example.ordersystem.model.OrderRequestResponse.RegistrationStatus.REJECTED;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;


    public RegistrationStatus registerOrder(OrderRequest order) {

        if (isOrderNotValid(order)) {
            return REJECTED;
        }

        var entity = mapper.map(order);
        repository.save(entity);

        return REGISTERED;
    }


    private boolean isOrderNotValid(OrderRequest order) {
        return order.getAmount() < 0;
    }
}

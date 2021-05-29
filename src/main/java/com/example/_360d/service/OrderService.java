package com.example._360d.service;

import com.example._360d.mapper.OrderMapper;
import com.example._360d.model.OrderRequest;
import com.example._360d.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example._360d.model.OrderRequestResponse.RegistrationStatus;
import static com.example._360d.model.OrderRequestResponse.RegistrationStatus.REGISTERED;
import static com.example._360d.model.OrderRequestResponse.RegistrationStatus.REJECTED;

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
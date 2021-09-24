package com.example.ordersystem.service;

import com.example.ordersystem.repository.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotifier {

    public void sendSuccessEmail(OrderEntity order) {
        log.info("success email sent");
    }

    public void sendFailureEmail(OrderEntity order) {
        log.info("failure email sent");
    }
}


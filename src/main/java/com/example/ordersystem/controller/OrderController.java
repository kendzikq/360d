package com.example.ordersystem.controller;

import com.example.ordersystem.model.OrderRequest;
import com.example.ordersystem.model.OrderRequestResponse;
import com.example.ordersystem.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.ordersystem.model.OrderRequestResponse.RegistrationStatus;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;


    @Operation(description = "The endpoint used to register an order from a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400")
    })
    @PostMapping(
            value = "v1/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    OrderRequestResponse requestOrder(@Valid @RequestBody OrderRequest order) {

        log.info("Requested the order: {}", order);

        RegistrationStatus status = service.registerOrder(order);

        return new OrderRequestResponse(status);
    }
}

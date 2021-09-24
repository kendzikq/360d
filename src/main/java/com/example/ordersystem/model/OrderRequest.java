package com.example.ordersystem.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class OrderRequest {

    @Email
    @NotNull
    private String email;

    @NotNull
    private Long amount;

    @NotNull
    private Currency currency;

    @Valid
    @NotNull
    private Address address;

    @Valid
    @NotNull
    private Product products;
}

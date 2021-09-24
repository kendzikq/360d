package com.example.ordersystem.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class Product {

    @NotNull
    private ProductName name;

    @Min(1)
    private int quantity;

}

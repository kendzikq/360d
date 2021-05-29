package com.example._360d.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class Products {

    @NotNull
    private ProductName name;

    @Min(1)
    private int quantity;

}

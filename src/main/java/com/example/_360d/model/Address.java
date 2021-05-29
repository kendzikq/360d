package com.example._360d.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Address {

    @NotNull
    private String street;

    @NotNull
    private String zipCode;

    @NotNull
    private String city;

    @NotNull
    private String country;
}

package com.example._360d.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
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

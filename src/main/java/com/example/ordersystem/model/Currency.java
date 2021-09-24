package com.example.ordersystem.model;

import lombok.Getter;

@Getter
public enum Currency {
    EUR("EUR"),
    PLN("PLN"),
    USD("USD");

    Currency(String value) {
        this.value = value;
    }

    private String value;

}

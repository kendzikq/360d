package com.example._360d.model;

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

package com.example.ordersystem.client;

import com.example.ordersystem.model.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static com.example.ordersystem.model.Currency.EUR;
import static com.example.ordersystem.model.Currency.PLN;
import static com.example.ordersystem.model.Currency.USD;
import static com.example.ordersystem.model.Currency.valueOf;
import static com.example.ordersystem.model.NBPResponse.Rate;
import static java.util.stream.Collectors.toMap;

@Component
@RequiredArgsConstructor
public class NBPClientWrapper {

    private final NBPClient nbpClient;

    private static final Set<String> HANDLED_CURRENCIES = Set.of(
            PLN.name(),
            EUR.name(),
            USD.name());

    public Map<Currency, Double> getRates() {

        var nbpResponse = nbpClient.getRates();

        var rates = Arrays.stream(nbpResponse.getContent()[0].getRates())
                .filter(rate -> HANDLED_CURRENCIES.contains(rate.getCode()))
                .collect(toMap(rate -> valueOf(rate.getCode()), Rate::getMid));
        rates.put(PLN, 1.0);

        return rates;
    }
}

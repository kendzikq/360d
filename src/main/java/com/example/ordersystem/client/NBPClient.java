package com.example.ordersystem.client;

import com.example.ordersystem.model.NBPResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "NBPClient", url = "https://api.nbp.pl/api/exchangerates/tables/a/")
public interface NBPClient {

    @Cacheable(value = "nbp", key = "'currency'")
    @GetMapping
    NBPResponse getRates();

}

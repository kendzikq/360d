package com.example._360d.controller;

import com.example._360d.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
class OrderController {

    @PostMapping(
            value = "v1/users/{userId}/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    void requestOrder(@RequestBody Order order, @PathVariable("userId") String userId) {

        //TODO is value in the pathVariable needed
        //TODO add validation
        //TODO generate swagger
        //TODO display api as web swagger
        
        log.info("User: {} request the order: {}", userId, order);



    }


}

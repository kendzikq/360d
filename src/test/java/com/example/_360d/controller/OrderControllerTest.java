package com.example._360d.controller;

import com.example._360d.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class OrderControllerTest extends BaseTest {


    @CsvSource({
            "23, REGISTERED, positive number should register an order",
            "-15, REJECTED, negative number should not register an order",
    })
    @ParameterizedTest
    void testOrderRequest(long amount, String result) throws Exception {

        // when - then
        mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{" +
                        "  \"email\": \"email@domain.com\",\n" +
                        "  \"amount\": " + amount + ",\n" +
                        "  \"currency\": \"EUR\",\n" +
                        "  \"address\": {\n" +
                        "    \"street\": \"Reymonta\",\n" +
                        "    \"zipCode\": \"30-059\",\n" +
                        "    \"city\": \"Krakow\",\n" +
                        "    \"country\": \"Poland\"\n" +
                        "  },\n" +
                        "  \"products\": {\n" +
                        "    \"name\": \"IPHONE_5\",\n" +
                        "    \"quantity\": 2\n" +
                        "  }\n" +
                        "}"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status").value(result));
    }
}
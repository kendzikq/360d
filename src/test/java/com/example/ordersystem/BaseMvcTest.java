package com.example.ordersystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class BaseMvcTest extends BaseTest{

    @Autowired
    protected MockMvc mockMvc;
}

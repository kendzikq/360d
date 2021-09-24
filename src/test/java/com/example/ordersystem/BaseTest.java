package com.example.ordersystem;

import com.example.ordersystem.model.AntiFraudStatus;
import com.example.ordersystem.model.Currency;
import com.example.ordersystem.model.ProductName;
import com.example.ordersystem.repository.OrderRepository;
import com.example.ordersystem.repository.entity.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class BaseTest {

    @Autowired
    protected OrderRepository orderRepository;

    @BeforeEach
    void prepareDB() {

        orderRepository.deleteAll();
        orderRepository.saveAll(List.of(
                order(3000L, Currency.EUR),
                order(5000L, Currency.USD)
        ));
    }


    private OrderEntity order(Long amount, Currency currency) {
        return OrderEntity.builder()
                .amount(amount)
                .antiFraudStatus(AntiFraudStatus.ACCEPTED)
                .currency(currency)
                .email("a@a.pl")
                .productName(ProductName.IPHONE_5)
                .productQuantity(1)
                .userCity("City")
                .userCountry("Country")
                .userZipcode("65-222")
                .userStreet("street")
                .build();
    }
}

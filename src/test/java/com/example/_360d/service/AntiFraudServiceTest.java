package com.example._360d.service;

import com.example._360d.BaseTest;
import com.example._360d.client.NBPClient;
import com.example._360d.model.Address;
import com.example._360d.model.Currency;
import com.example._360d.model.NBPResponse;
import com.example._360d.model.OrderRequest;
import com.example._360d.model.Product;
import com.example._360d.model.ProductName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static com.example._360d.model.NBPResponse.Content;
import static com.example._360d.model.NBPResponse.Rate;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class AntiFraudServiceTest extends BaseTest {

    @Autowired
    private AntiFraudService antiFraudService;

    @Autowired
    private OrderService orderService;

    @MockBean
    private NBPClient nbpClient;

    @MockBean
    private EmailNotifier emailNotifier;


    private static final Double EUR_RATE = 5.0;
    private static final Double USD_RATE = 4.0;


    @Test
    void verifyAntiFraudService() {

        // given
        final long SMALL_AMOUNT = 5L;
        final long BIG_AMOUNT = 500000000000000L;

        when(nbpClient.getRates()).thenReturn(buildNBPResponse());
        orderService.registerOrder(order(SMALL_AMOUNT));

        // when
        antiFraudService.monitor();

        // then
        verify(emailNotifier, times(1)).sendSuccessEmail(any()); // below average

        //when
        orderService.registerOrder(order(BIG_AMOUNT));

        // when
        antiFraudService.monitor();

        // then
        verify(emailNotifier, times(1)).sendFailureEmail(any()); // above average

    }

    private OrderRequest order(Long amount) {

        return OrderRequest.builder()
                .address(Address.builder()
                        .city("Sunny City")
                        .country("San Eskobar")
                        .street("Green Pot")
                        .zipCode("23-123")
                        .build())
                .amount(amount)
                .currency(Currency.PLN)
                .email("a@a.pl")
                .products(Product.builder()
                        .name(ProductName.BOSE_HEADSET)
                        .quantity(1)
                        .build())
                .build();
    }


    private NBPResponse buildNBPResponse() {

        Rate eur = new Rate("EUR", "EUR", EUR_RATE);
        Rate usd = new Rate("USD", "USD", USD_RATE);

        Content content = new Content("", "", LocalDate.now(), new Rate[]{eur, usd});
        return new NBPResponse(new Content[]{content});
    }
}
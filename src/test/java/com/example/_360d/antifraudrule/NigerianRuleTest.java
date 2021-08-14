package com.example._360d.antifraudrule;

import com.example._360d.client.NBPClientWrapper;
import com.example._360d.model.Currency;
import com.example._360d.repository.entity.OrderEntity;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example._360d.model.Currency.EUR;
import static com.example._360d.model.Currency.PLN;
import static com.example._360d.model.Currency.USD;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NigerianRuleTest {

    private static final double MAX_AMOUNT_PLN = 100000.00;

    @Mock
    private NBPClientWrapper nbpClientWrapper;

    @InjectMocks
    private NigerianRule nigerianRule;


    @CsvSource({"SAN_ESCOBAR, 100001, PLN, false, applies only to Nigeria country !",
            "SAN_ESCOBAR, 99999, PLN, false",
            "NIGERIA, 100001, PLN, true",
            "NIGERIA, 99999, PLN, false"}
    )
    @ParameterizedTest
    void nigerianTest(String country, long orderAmount, Currency orderCurrency, boolean isFraud) {

        // given
        when(nbpClientWrapper.getRates()).thenReturn(ofEntries(
                entry(PLN, 1.0),
                entry(EUR, 5.0),
                entry(USD, 4.0)
        ));

        var order = OrderEntity.builder()
                .userCountry(country)
                .amount(orderAmount)
                .currency(orderCurrency)
                .build();

        // when - then
        assertThat(nigerianRule.isFraud(order)).isEqualTo(isFraud);
    }

}
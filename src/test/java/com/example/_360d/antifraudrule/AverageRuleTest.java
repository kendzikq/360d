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

import static com.example._360d.antifraudrule.AverageRule.AVGCalculator;
import static com.example._360d.model.Currency.EUR;
import static com.example._360d.model.Currency.PLN;
import static com.example._360d.model.Currency.USD;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AverageRuleTest {

    @Mock
    private NBPClientWrapper nbpClientWrapper;
    @Mock
    private AVGCalculator avgCalculator;

    @InjectMocks
    private AverageRule averageRule;

    /**
     * multiplication factor = 5
     * EUR/PLN = 5.0
     * USD/PLN = 4.0
     */
    @CsvSource({"1000.0, 5001, PLN, true, because 5001 > 1000 * 5",
            "1000.0, 4999, PLN, false, because 4999 < 1000 * 5",
            "1000.0, 1001, EUR, true, because 1001 * 5 > 1000 * 5",
            "1000.0, 999, EUR, false, because 999 * 5 < 1000 * 5",
            "1000.0, 1251, USD, true, because 1251 * 4 > 1000 * 5",
            "1000.0, 1249, USD, false, because 1249 * 4 < 1000 * 5"}
    )
    @ParameterizedTest
    void averageTest(double avgPLN, long orderAmount, Currency orderCurrency, boolean isFraud) {

        // given
        when(avgCalculator.calculateAVGInPLN()).thenReturn(avgPLN);
        when(nbpClientWrapper.getRates()).thenReturn(ofEntries(
                entry(PLN, 1.0),
                entry(EUR, 5.0),
                entry(USD, 4.0)
        ));

        var order = OrderEntity.builder()
                .amount(orderAmount)
                .currency(orderCurrency)
                .build();

        // when - then
        assertThat(averageRule.isFraud(order)).isEqualTo(isFraud);
    }
}
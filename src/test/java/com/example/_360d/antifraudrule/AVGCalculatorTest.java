package com.example._360d.antifraudrule;

import com.example._360d.client.NBPClientWrapper;
import com.example._360d.repository.OrderRepository;
import com.example._360d.repository.entity.OrderEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example._360d.antifraudrule.AverageRule.AVGCalculator;
import static com.example._360d.model.AntiFraudStatus.ACCEPTED;
import static com.example._360d.model.Currency.EUR;
import static com.example._360d.model.Currency.PLN;
import static com.example._360d.model.Currency.USD;
import static com.example._360d.repository.entity.OrderEntity.builder;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AVGCalculatorTest {

    @Mock
    private NBPClientWrapper nbpClientWrapper;
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private AVGCalculator avgCalculator;

    @Test
    void calculateAvg() {

        // given
        double avg = 1000.0;
        when(orderRepository.findAllByAntiFraudStatus(eq(ACCEPTED))).thenReturn(getOrdersForAVG());
        when(nbpClientWrapper.getRates()).thenReturn(ofEntries(
                entry(PLN, 1.0),
                entry(EUR, 5.0),
                entry(USD, 4.0)
        ));

        // when - then
        assertThat(avgCalculator.calculateAVGInPLN()).isEqualTo(avg);
    }

    private List<OrderEntity> getOrdersForAVG() {

        return List.of(builder().amount(1000L).currency(PLN).build(),
                builder().amount(200L).currency(EUR).build(),
                builder().amount(250L).currency(USD).build());
    }

}
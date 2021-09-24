package com.example.ordersystem.repository.entity;

import com.example.ordersystem.model.AntiFraudStatus;
import com.example.ordersystem.model.Currency;
import com.example.ordersystem.model.ProductName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Builder
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    @SequenceGenerator(name = "order_sequence", sequenceName = "seq_orders_id", allocationSize = 1)
    private Long id;

    private String email;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private String userStreet;

    private String userZipcode;

    private String userCity;

    private String userCountry;

    @Enumerated(EnumType.STRING)
    private ProductName productName;

    private Integer productQuantity;

    @Enumerated(EnumType.STRING)
    private AntiFraudStatus antiFraudStatus;

}

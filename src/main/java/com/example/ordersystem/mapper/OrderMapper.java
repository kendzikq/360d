package com.example.ordersystem.mapper;

import com.example.ordersystem.model.OrderRequest;
import com.example.ordersystem.repository.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "antiFraudStatus", constant = "PENDING")
    @Mapping(target = "userStreet", source = "address.street")
    @Mapping(target = "userZipcode", source = "address.zipCode")
    @Mapping(target = "userCity", source = "address.city")
    @Mapping(target = "userCountry", source = "address.country")
    @Mapping(target = "productName", source = "products.name")
    @Mapping(target = "productQuantity", source = "products.quantity")
    OrderEntity map(OrderRequest order);
}

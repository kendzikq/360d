package com.example.ordersystem.repository;

import com.example.ordersystem.model.AntiFraudStatus;
import com.example.ordersystem.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByAntiFraudStatus(AntiFraudStatus antiFraudStatus);

    @Modifying
    @Query("UPDATE OrderEntity entity SET entity.antiFraudStatus = ?1 WHERE entity.id = ?2")
    void changeStatus(AntiFraudStatus status, Long orderId);
}

package com.example._360d.repository;

import com.example._360d.model.AntiFraudStatus;
import com.example._360d.model.Currency;
import com.example._360d.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByAntiFraudStatus(AntiFraudStatus antiFraudStatus);

}

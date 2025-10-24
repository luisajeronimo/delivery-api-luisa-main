package com.deliverytech.delivery.repository.OrderFolder;

import com.deliverytech.delivery.entity.OrderFolder.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}

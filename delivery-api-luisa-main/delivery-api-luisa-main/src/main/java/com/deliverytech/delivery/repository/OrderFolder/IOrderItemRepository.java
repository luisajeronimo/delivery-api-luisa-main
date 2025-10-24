package com.deliverytech.delivery.repository.OrderFolder;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery.entity.OrderFolder.OrderItem;

public interface IOrderItemRepository extends JpaRepository<OrderItem, Long> {
}
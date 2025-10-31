package com.deliverytech.delivery.repository.OrderFolder;

import com.deliverytech.delivery.entity.OrderFolder.Order;
import com.deliverytech.delivery.entity.OrderFolder.OrderStatus;
import com.deliverytech.delivery.entity.CustomerFolder.Customer;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {

    // Buscar pedidos por cliente
    List<Order> findByCustomerOrderByOrderDateDesc(Customer customer);
 
    // Buscar pedidos por cliente ID
    List<Order> findByCustomerIdOrderByOrderDateDesc(Long customerId);

    // Buscar por status
    List<Order> findByOrderStatusOrderByOrderDateDesc(OrderStatus status);
 
    // Buscar pedidos por período
    List<Order> findByOrderDateBetweenOrderByOrderDateDesc(LocalDateTime inicio, LocalDateTime fim);
    List<Order> findByStatusAndOrderDateBetweenOrderByOrderDateDesc(OrderStatus status, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);
    List<Order> findByRestaurantIdAndStatusOrderByOrderDateDesc(Long id, OrderStatus status);

}


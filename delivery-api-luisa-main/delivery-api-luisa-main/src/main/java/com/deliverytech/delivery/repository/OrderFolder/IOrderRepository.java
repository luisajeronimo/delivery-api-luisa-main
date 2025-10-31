package com.deliverytech.delivery.repository.OrderFolder;

import com.deliverytech.delivery.entity.OrderFolder.Order;
import com.deliverytech.delivery.entity.OrderFolder.OrderStatus;
import com.deliverytech.delivery.entity.CustomerFolder.Customer;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
    /**
     * Repositório JPA para operações de leitura/gravação de {@link com.deliverytech.delivery.entity.OrderFolder.Order}.
     *
     * Métodos definidos abaixo usam a convenção de nomes do Spring Data para gerar queries automaticamente.
     */

    // Buscar pedidos por cliente
    List<Order> findByCustomerOrderByOrderDateDesc(Customer customer);
 
    // Buscar pedidos por cliente ID
    List<Order> findByCustomerIdOrderByOrderDateDesc(Long customerId);

    // Buscar por status
    List<Order> findByOrderStatusOrderByOrderDateDesc(OrderStatus status);
 
    // Buscar pedidos por período
    List<Order> findByOrderDateBetweenOrderByOrderDateDesc(LocalDateTime inicio, LocalDateTime fim);
    
    // Buscar por status dentro de um intervalo de datas com paginação
    List<Order> findByOrderStatusAndOrderDateBetweenOrderByOrderDateDesc(OrderStatus status, LocalDateTime inicio, LocalDateTime fim, Pageable pageable);

    // Buscar pedidos de um restaurante filtrados por status
    List<Order> findByRestaurantIdAndOrderStatusOrderByOrderDateDesc(Long id, OrderStatus status);

}


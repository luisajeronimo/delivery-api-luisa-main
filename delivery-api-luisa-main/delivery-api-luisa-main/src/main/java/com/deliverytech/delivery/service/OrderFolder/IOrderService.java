package com.deliverytech.delivery.service.OrderFolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import com.deliverytech.delivery.dto.OrderFolder.OrderDTO;
import com.deliverytech.delivery.entity.OrderFolder.OrderStatus;
 
public interface IOrderService {

 OrderDTO createOrder(OrderDTO dto);
 OrderDTO findOrderById(Long id);
 List<OrderDTO> listOrders(OrderStatus status, LocalDateTime datainicio, LocalDateTime datafim, Pageable pageable);
 OrderDTO updateOrderStatus(Long id, OrderStatus status);
 void cancelOrder(Long id);
 List<OrderDTO> findOrdersByCustomer(Long id);
 List<OrderDTO> findOrdersByRestaurant(Long id, OrderStatus status);
 BigDecimal calculateTotalOrder(Long id);
}
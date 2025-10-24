package com.deliverytech.delivery.service.OrderFolder;

import com.deliverytech.delivery.dto.OrderFolder.OrderDTO;
import com.deliverytech.delivery.dto.OrderFolder.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO createOrder(OrderDTO orderDTO);
    OrderResponseDTO updateOrder(Long orderId, OrderDTO orderDTO);
    OrderResponseDTO getOrder(Long orderId);
    void deleteOrder(Long orderId);
}
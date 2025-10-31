package com.deliverytech.delivery.service.OrderFolder;

import com.deliverytech.delivery.dto.OrderFolder.OrderItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderItemService {
    
    List<OrderItemDTO> getAllItemsOrder();
    OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO);
    OrderItemDTO updateOrderItem(Long orderItemId, OrderItemDTO orderItemDTO);
    OrderItemDTO getOrderItem(Long orderItemId);
    void deleteOrderItem(Long orderItemId);
}
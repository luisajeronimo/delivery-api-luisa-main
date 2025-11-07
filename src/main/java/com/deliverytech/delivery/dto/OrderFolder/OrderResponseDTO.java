package com.deliverytech.delivery.dto.OrderFolder;

import com.deliverytech.delivery.entity.OrderFolder.OrderItem;
import com.deliverytech.delivery.entity.OrderFolder.OrderStatus;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    
    private Long id;
    private Long customerId;
    private Long restaurantId;
    private String deliveryAddress;
    private String status;
    private String totalPrice;
    private LocalDateTime date;
    private OrderStatus orderStatus;
    private String note;
    private List<OrderItem> orderItemList;
}
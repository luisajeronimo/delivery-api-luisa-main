package com.deliverytech.delivery.dto.OrderFolder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class OrderDTO {

    @NotNull(message = "Customer is required")
    private Long customerId;

    @NotNull(message = "Restaurant is required")
    private Long restaurantId;

    @NotBlank(message = "Delivery address is required")
    @Size(max = 200, message = "Delivery address must be at most 200 characters")
    private String deliveryAddress;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItemDTO> items;

    @NotNull(message = "Order date is required")
    private String orderDate;

    // Getters e Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public Long getRestaurantId() { return restaurantId; }
    public void setRestaurantId(Long restaurantId) { this.restaurantId = restaurantId; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
}
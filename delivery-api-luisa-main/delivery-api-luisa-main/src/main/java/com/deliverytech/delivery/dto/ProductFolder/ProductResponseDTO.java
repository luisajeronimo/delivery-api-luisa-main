package com.deliverytech.delivery.dto.ProductFolder;

import lombok.Data;

import java.math.BigDecimal;

import com.deliverytech.delivery.entity.ProductFolder.ProductStatus;

@Data
public class ProductResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private ProductStatus available;
    private Long restaurantId;
}
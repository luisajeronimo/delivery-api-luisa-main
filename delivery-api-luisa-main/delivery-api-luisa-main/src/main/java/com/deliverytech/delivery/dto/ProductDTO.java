package com.deliverytech.delivery.dto;

import java.math.BigDecimal;

import com.deliverytech.delivery.entity.StatusProduct;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;    
    private String description;
    private String category;
    private BigDecimal price;
    private BigDecimal note;
    private StatusProduct status;  
}

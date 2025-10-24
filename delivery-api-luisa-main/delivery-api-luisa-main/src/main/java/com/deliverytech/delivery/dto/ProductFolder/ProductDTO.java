package com.deliverytech.delivery.dto.ProductFolder;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;


@Data
public class ProductDTO {

    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    @NotNull(message = "Product name cannot be null")
    private String name;

    private String description;

    @NotNull(message = "Product price cannot be null")
    private BigDecimal price;

    private String image;

    @NotNull(message = "Product category cannot be null")
    private String category;
    
    @NotNull(message = "Product quantity must be greater than 0")
    @Min(value = 1, message = "Product quantity must be greater than 0")
    @Max(value = 100, message = "Product quantity must be less than 100")
    private int quantity;
}
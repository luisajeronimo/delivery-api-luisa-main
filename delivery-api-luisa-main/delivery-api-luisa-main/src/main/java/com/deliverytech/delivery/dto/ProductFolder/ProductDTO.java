package com.deliverytech.delivery.dto.ProductFolder;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class ProductDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 25, message = "Name must be at most 25 characters")
    private String name;

    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description;

    private BigDecimal price;
    private String category;
    private boolean available;

    @Positive(message = "Restaurant ID is required")
    private Long restaurantId;
}
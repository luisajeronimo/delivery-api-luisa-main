package com.deliverytech.delivery.dto.RestaurantFolder;
 
import java.math.BigDecimal;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
 
@Data
public class RestaurantDTO {

    @NotBlank(message = "Name is required")
    @Size(max = 25, message = "Name must be at most 25 characters")
    private String name;

    @NotBlank(message = "Category is required")
    @Size(max = 50, message = "Category must be at most 50 characters")
    private String category;

    @NotBlank(message = "Address is required")
    @Size(max = 100, message = "Address must be at most 100 characters")
    private String address;

    @NotBlank(message = "ZIP code is required")
    @Size(max = 8, message = "ZIP code must be at most 8 characters")
    private String cep;

    @NotBlank(message = "Phone is required")
    @Size(max = 10, message = "Phone must be at most 10 characters")
    private String phone;

    private BigDecimal deliveryFee;

    @NotBlank(message = "CNPJ is required")
    @Size(max = 14, message = "CNPJ must be at most 14 characters")
    private String cnpj;
    private boolean active;
}
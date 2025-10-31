package com.deliverytech.delivery.dto.RestaurantFolder;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RestaurantResponseDTO {
    
    private Long id;
    private String name;
    private String category;
    private String address;
    private String zipCode;
    private String phone;
    private BigDecimal deliveryFee;
    private String CNPJ;
    private boolean isActive;
    private LocalDateTime registrationDate;
}
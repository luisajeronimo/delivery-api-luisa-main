package com.deliverytech.delivery.dto.RestaurantFolder;

import lombok.Data;
import java.util.Map;
import java.time.DayOfWeek;

@Data
public class RestaurantResponseDTO {
    
    private Long id;
    private String image;
    private String rating;
    private boolean active;
    private String description;
    private String name;
    private String cuisine;
    private String address;
    private String cnpj;
    private String phone;
    private Map<DayOfWeek,WorkHoursDTO> workHours;
}
package com.deliverytech.delivery.dto.RestaurantFolder;

import lombok.Data;

import java.time.LocalTime;

@Data
public class WorkHoursDTO {
    
    private LocalTime start;
    private LocalTime end;
}

package com.deliverytech.delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.RestaurantDTO;

@Service
public interface IRestaurantService {
    List<RestaurantDTO> getAllRestaurants();
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);
}
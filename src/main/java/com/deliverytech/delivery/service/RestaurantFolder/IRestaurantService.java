package com.deliverytech.delivery.service.RestaurantFolder;

import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantDTO;
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IRestaurantService {
    
    List<RestaurantResponseDTO> getAllRestaurants();
    RestaurantResponseDTO createRestaurant(RestaurantDTO restaurantDTO);
    RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO);
    RestaurantResponseDTO getRestaurant(Long restaurantId);
    void deleteRestaurant(Long restaurantId);
}
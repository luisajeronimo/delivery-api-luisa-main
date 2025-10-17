package com.deliverytech.delivery.controller;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.deliverytech.delivery.dto.RestaurantDTO;
import com.deliverytech.delivery.service.IRestaurantService;
 
import jakarta.validation.Valid;
 
@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
 
    @Autowired
    private IRestaurantService restaurantService;
 
    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> list() {
        List<RestaurantDTO> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurants);
    }
 
    @PostMapping
    public ResponseEntity<RestaurantDTO> create(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO restaurant = restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }
}
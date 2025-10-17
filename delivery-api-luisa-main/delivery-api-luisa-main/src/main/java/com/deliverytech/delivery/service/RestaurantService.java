package com.deliverytech.delivery.service;
 
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.RestaurantDTO;
import com.deliverytech.delivery.entity.Restaurant;
import com.deliverytech.delivery.repository.IRestaurantRepository;

import org.modelmapper.ModelMapper;
 
@Service
public class RestaurantService implements IRestaurantService {
 
    @Autowired
    private IRestaurantRepository repository;
 
    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
       ModelMapper mapper = new ModelMapper();
 
       Restaurant entity = mapper.map(restaurantDTO, Restaurant.class);
       Restaurant restaurant = repository.save(entity);
       RestaurantDTO dto = mapper.map(restaurant, RestaurantDTO.class);
 
       return dto;
    }
 
    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        ModelMapper mapper = new ModelMapper();  
        List<Restaurant> restaurantes = repository.findAll();
 
        List<RestaurantDTO> restaurantDtoList = Arrays.asList(mapper.map(restaurantes, RestaurantDTO[].class));
 
        return restaurantDtoList;
        //return repository.findAll().stream().map(this::ConvertEntityToDto).collect(Collectors.toList());
    }
     
}
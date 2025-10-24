package com.deliverytech.delivery.service.RestaurantFolder;
 
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantDTO;
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantResponseDTO;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;
import com.deliverytech.delivery.repository.RestaurantFolder.IRestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

    public RestaurantService() {
        super();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponseDTO> getAllRestaurants() {
        ModelMapper modelMapper = new ModelMapper();
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return Arrays.asList(modelMapper.map(restaurants, RestaurantResponseDTO[].class));
    }

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantDTO restaurantDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Restaurant entity = modelMapper.map(restaurantDTO, Restaurant.class);
        Restaurant restaurant = restaurantRepository.save(entity);
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    @Transactional
    public RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO) {
        ModelMapper modelMapper = new ModelMapper();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            return null;
        }
        modelMapper.map(restaurantDTO, restaurant);
        restaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    @Override
    public RestaurantResponseDTO getRestaurant(Long restaurantId) {
        ModelMapper modelMapper = new ModelMapper();
        return restaurantRepository.findById(restaurantId).map(restaurant -> modelMapper.map(restaurant, RestaurantResponseDTO.class)).orElse(null);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
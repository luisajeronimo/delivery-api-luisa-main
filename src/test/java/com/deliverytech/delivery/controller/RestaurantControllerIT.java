package com.deliverytech.delivery.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.deliverytech.delivery.repository.RestaurantFolder.IRestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;  
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantDTO;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RestaurantControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IRestaurantRepository restaurantRepository;

    private RestaurantDTO restaurantDTO;
    private Restaurant registeredRestaurant;

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();

    restaurantDTO = new RestaurantDTO();
    restaurantDTO.setName("Pizza Express");
    restaurantDTO.setCategory("Italiana");
    restaurantDTO.setAddress("Rua das Flores, 123");
    // DTO requires 8-char cep and 14-char cnpj and phone within size limits
    restaurantDTO.setCep("01234567");
    restaurantDTO.setCnpj("12345678000199");
    restaurantDTO.setPhone("1199999999");
    restaurantDTO.setDeliveryFee(new BigDecimal("5.50"));
    restaurantDTO.setWorkHours("08:00-22:00");

        // Criar restaurant para testes de busca
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Burger King");
        restaurant.setCategory("Americana");
        restaurant.setAddress("Av. Paulista, 1000");
        restaurant.setCep("12345678");
        restaurant.setCnpj("98765432000177");
        restaurant.setPhone("1188888888");
        restaurant.setDeliveryFee(null);
        restaurant.setActive(true);
        registeredRestaurant = restaurantRepository.save(restaurant);
    }

    @Test
    void shouldRegisterRestaurantSuccessfully() throws Exception {
        mockMvc.perform(post("/api/v1/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Pizza Express"))
                .andExpect(jsonPath("$.category").value("Italiana"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldRejectInvalidRestaurant() throws Exception {
        restaurantDTO.setName(""); // Nome inválido
        restaurantDTO.setPhone("123"); // Telefone inválido

        mockMvc.perform(post("/api/v1/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.details.name").exists())
                .andExpect(jsonPath("$.details.phone").exists());
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        mockMvc.perform(get("/api/v1/restaurants/{id}", registeredRestaurant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(registeredRestaurant.getId()))
                .andExpect(jsonPath("$.name").value("Burger King"));
    }

    @Test
    void shouldReturn404ForNonExistentRestaurant() throws Exception {
        mockMvc.perform(get("/api/v1/restaurants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode").exists());
    }

    @Test
    void shouldListRestaurantsWithPagination() throws Exception {
        // Controller returns a list (not a pageable), so just assert an array is returned
        mockMvc.perform(get("/api/v1/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Burger King"));
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() throws Exception {
        restaurantDTO.setName("Pizza Express Atualizada");

        mockMvc.perform(put("/api/v1/restaurants/{id}", registeredRestaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pizza Express Atualizada"));

        // verify repository updated
        var opt = restaurantRepository.findById(registeredRestaurant.getId());
        assertTrue(opt.isPresent());
        assertEquals("Pizza Express Atualizada", opt.get().getName());
    }

    @Test
    void shouldDeleteRestaurantSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurants/{id}", registeredRestaurant.getId()))
                .andExpect(status().isNoContent());

        assertFalse(restaurantRepository.findById(registeredRestaurant.getId()).isPresent());
    }
}

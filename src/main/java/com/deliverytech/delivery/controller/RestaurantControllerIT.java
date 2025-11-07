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
        restaurantDTO.setPhone("11999999999");
        restaurantDTO.setDeliveryFee(new BigDecimal("5.50"));
        restaurantDTO.setWorkHours("08:00-22:00");

        // Criar restaurant para testes de busca
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Burger King");
        restaurant.setCategory("Americana");
        restaurant.setAddress("Av. Paulista, 1000");
        restaurant.setPhone("11888888888");
        restaurant.setDeliveryFee(null);
        //restaurant.setWorkHours("10:00-23:00");
        restaurant.setActive(true);
        registeredRestaurant = restaurantRepository.save(restaurant);
    }

    @Test
    void shouldRegisterRestaurantSuccessfully() throws Exception {
        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isCreated());
                //.andExpect(jsonPath("$.success").value(true))
                //.andExpect(jsonPath("$.data.nome").value("Pizza Express"))
                //.andExpect(jsonPath("$.data.categoria").value("Italiana"))
                //.andExpect(jsonPath("$.data.ativo").value(true))
                //.andExpect(jsonPath("$.message").value("restaurant criado com sucesso"));
    }

    @Test
    void shouldRejectInvalidRestaurant() throws Exception {
        restaurantDTO.setName(""); // Nome inválido
        restaurantDTO.setPhone("123"); // Telefone inválido

        mockMvc.perform(post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isBadRequest());
                //.andExpect(jsonPath("$.success").value(false))
                //.andExpect(jsonPath("$.error.code").value("VALIDATION_ERROR"))
                //.andExpect(jsonPath("$.error.details.nome").exists())
                //.andExpect(jsonPath("$.error.details.telefone").exists());
    }

    @Test
    void shouldFindRestaurantById() throws Exception {
        mockMvc.perform(get("/api/restaurants/{id}", registeredRestaurant.getId()))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.success").value(true))
                //.andExpect(jsonPath("$.data.id").value(restaurantSalvo.getId()))
                //.andExpect(jsonPath("$.data.nome").value("Burger King"))
                //.andExpect(jsonPath("$.data.categoria").value("Americana"));
    }

    @Test
    void shouldReturn404ForNonExistentRestaurant() throws Exception {
        mockMvc.perform(get("/api/restaurants/{id}", 999L))
                .andExpect(status().isNotFound());
                //.andExpect(jsonPath("$.success").value(false))
                //.andExpect(jsonPath("$.error.code").value("ENTITY_NOT_FOUND"));
    }

    @Test
    void shouldListRestaurantsWithPagination() throws Exception {
        mockMvc.perform(get("/api/restaurants")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.content").isArray())
                //.andExpect(jsonPath("$.content", hasSize(1)))
                //.andExpect(jsonPath("$.page.number").value(0))
                //.andExpect(jsonPath("$.page.size").value(10))
                //.andExpect(jsonPath("$.page.totalElements").value(1));
    }

    @Test
    void shouldUpdateRestaurantSuccessfully() throws Exception {
        restaurantDTO.setName("Pizza Express Atualizada");

        mockMvc.perform(put("/api/restaurants/{id}", registeredRestaurant.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantDTO)))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.success").value(true))
                //.andExpect(jsonPath("$.data.nome").value("Pizza Express Atualizada"))
                //.andExpect(jsonPath("$.message").value("restaurant atualizado com sucesso"));
    }

    @Test
    void shouldChangeRestaurantStatus() throws Exception {
        mockMvc.perform(patch("/api/restaurants/{id}/status", registeredRestaurant.getId()))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.success").value(true))
                //.andExpect(jsonPath("$.data.ativo").value(false))
                //.andExpect(jsonPath("$.message").value("Status alterado com sucesso"));
    }

    @Test
    void shouldFindRestaurantsByCategory() throws Exception {
        mockMvc.perform(get("/api/restaurants/categoria/{categoria}", "Americana"))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.success").value(true))
                //.andExpect(jsonPath("$.data").isArray())
                //.andExpect(jsonPath("$.data", hasSize(1)))
                //.andExpect(jsonPath("$.data[0].categoria").value("Americana"));
    }
}


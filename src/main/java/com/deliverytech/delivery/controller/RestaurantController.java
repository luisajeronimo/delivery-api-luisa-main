package com.deliverytech.delivery.controller;
 
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantDTO;
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantResponseDTO;
import com.deliverytech.delivery.service.RestaurantFolder.IRestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de Restaurantes.
 * Base URL: /api/v1/restaurants
 * Observação: @SecurityRequirement é apenas para documentação (Swagger). 
 * A proteção real é feita pelo Spring Security (JWT no header Authorization).
 */
@RestController
@RequestMapping("/api/v1/restaurants")
@Tag(name = "Restaurants", description = "Operations related to restaurants")
public class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;

    @Operation(
            summary = "All restaurants listed",
            description = "Returns the list of registered Restaurants",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Restaurants listed successfully",
                        content = @Content(mediaType = "application/json")
                )
            }
    )

    // Requer token Bearer no Swagger (documentação)
    /**
     * GET /api/v1/restaurants
     * Lista todos os restaurantes.
     * Retorna 200 OK com a lista no corpo.
     */
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> list() {
        List<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @Operation(
            summary = "Register a new Restaurant",
            description = "Registers a new Restaurant",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Restaurant registered successfully",
                        content = @Content(mediaType = "application/json")
                )
            }
    )
    
    // Requer token Bearer no Swagger (documentação)
    /**
     * POST /api/v1/restaurants
     * Cria um novo restaurante a partir do JSON recebido.
     * Valida o payload (DTO) e retorna 201 Created com o recurso criado.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantResponseDTO restaurant = restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    // Requer token Bearer no Swagger (documentação)
    /**
     * GET /api/v1/restaurants/{restaurantId}
     * Busca um restaurante pelo ID.
     * Retorna 200 OK com o restaurante encontrado.
     */
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurant(@Valid @PathVariable("restaurantId") Long restaurantId) {
        RestaurantResponseDTO restaurant = restaurantService.getRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    // Requer token Bearer no Swagger (documentação)
    /**
     * PUT /api/v1/restaurants/{restaurantId}
     * Atualiza os dados de um restaurante existente pelo ID.
     * Retorna 200 OK com o restaurante atualizado.
     */
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/{restaurantId}" )
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable("restaurantId") Long restaurantId, 
    @Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantResponseDTO updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRestaurant);
    }

    // Requer token Bearer no Swagger (documentação)
    /**
     * DELETE /api/v1/restaurants/{restaurantId}
     * Remove um restaurante pelo ID.
     * Retorna 204 No Content em caso de sucesso.
     */
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/{restaurantId}" )
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
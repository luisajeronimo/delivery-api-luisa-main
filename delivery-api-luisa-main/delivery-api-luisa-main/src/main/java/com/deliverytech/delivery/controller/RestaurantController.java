package com.deliverytech.delivery.controller;
 
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantDTO;
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantResponseDTO;
import com.deliverytech.delivery.service.RestaurantFolder.IRestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;

    @Operation(
            summary = "Lista todos os Restaurantes",
            description = "Retorna a lista de Restaurantes registrados",
            responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Restaurantes listados com sucesso",
                        content = @Content(mediaType = "application/json")
                )
            }
    )

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<List<RestaurantResponseDTO>> list() {
        List<RestaurantResponseDTO> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(restaurants);
    }

    @Operation(
            summary = "Cadastra um novo Restaurante",
            description = "Cadastra um novo Restaurante",
            responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "Restaurante cadastrado com sucesso",
                        content = @Content(mediaType = "application/json")
                )
            }
    )

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<RestaurantResponseDTO> create(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantResponseDTO restaurant = restaurantService.createRestaurant(restaurantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurant);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/{restaurantId}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurant(@Valid @PathVariable("restaurantId") Long restaurantId) {
        RestaurantResponseDTO restaurant = restaurantService.getRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(restaurant);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/{restaurantId}" )
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable("restaurantId") Long restaurantId, 
    @Valid @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantResponseDTO updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedRestaurant);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/{restaurantId}" )
    public ResponseEntity<Void> deleteRestaurant(@PathVariable("restaurantId") Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
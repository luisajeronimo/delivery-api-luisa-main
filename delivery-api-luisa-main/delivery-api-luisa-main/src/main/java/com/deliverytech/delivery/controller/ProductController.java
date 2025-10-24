package com.deliverytech.delivery.controller;

import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.service.ProductFolder.IProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Produto", description = "Produto API")
public class ProductController {
    
    @Autowired
    private IProductService productService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/{restaurantId}")
    public ResponseEntity<List<ProductResponseDTO>> list(@PathVariable("restaurantId") Long restaurantId) {
        List<ProductResponseDTO> products = productService.getProductsByRestaurantId(restaurantId);
        return ResponseEntity.status(200).body(products);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<ProductResponseDTO> create(@PathVariable("restaurantId") Long restaurantId,
                                             @Valid @RequestBody ProductDTO productDTO) {
        ProductResponseDTO product = productService.createProduct(productDTO, restaurantId);
        return ResponseEntity.status(201).body(product);
    }
}

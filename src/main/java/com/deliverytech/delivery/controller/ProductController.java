package com.deliverytech.delivery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.service.ProductFolder.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;
 
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
@Tag(name = "Products", description = "Operations related to products")
public class ProductController {
    @Autowired
    private IProductService productService;
 
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    @Operation(summary = "Register product",
               description = "Creates a new product in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Product created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "404", description = "Restaurant not found")
    })
    public ResponseEntity<ProductResponseDTO> register(
            @Valid @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Product data to be created"
            ) ProductDTO dto) {
 
        ProductResponseDTO product = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
    
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/{id}")
    @Operation(summary = "Find product by ID",
               description = "Retrieve a specific product by its ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponseDTO> findById(
            @Parameter(description = "Product's ID")
            @PathVariable Long id) {

        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
 
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{id}")
    @Operation(summary = "Update product",
               description = "Update the details of an existing product")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<ProductResponseDTO> update(
            @Parameter(description = "Product's ID")
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {

        ProductResponseDTO product = productService.updateProduct(id, dto);
        return ResponseEntity.ok(product);
    }
 
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    @Operation(summary = "Remove product",
               description = "Remove a product from the system")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Product removed successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "409", description = "Product has associated orders")
    })
    public ResponseEntity<Void> remove(
            @Parameter(description = "Product's ID")
            @PathVariable Long id) {
 
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
 
    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping("/{id}/availability")
    @Operation(summary = "Change availability",
               description = "Toggle the availability of a product")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Availability changed successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponseDTO> changeProductStatus(
            @Parameter(description = "Products's ID")
            @PathVariable Long id,
            @Valid @RequestBody ProductDTO dto) {

        ProductResponseDTO product = productService.changeProductStatus(id, dto);
        return ResponseEntity.ok(product);
    }
 
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/category/{category}")
    @Operation(summary = "Find by category",
               description = "List products from a specific category",
               responses = {
                   @ApiResponse(responseCode = "200", 
                                description = "Products found", 
                                content = @Content(mediaType = "application/json")
                    )
                }
                )
    public ResponseEntity<List<ProductResponseDTO>> findByCategory(
            @Parameter(description = "Product's category")
            @PathVariable String category) {

        List<ProductResponseDTO> products = productService.getAllProductsByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }
 
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/find-by-name")
    @Operation(summary = "Find by name",
               description = "Search products by name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    public ResponseEntity<List<ProductResponseDTO>> findByName(
            @Parameter(description = "Product's name")
            @RequestParam String name) {

        List<ProductResponseDTO> products = productService.getAllProductsByNameSearch(name);
        return ResponseEntity.ok(products);
    }

    }
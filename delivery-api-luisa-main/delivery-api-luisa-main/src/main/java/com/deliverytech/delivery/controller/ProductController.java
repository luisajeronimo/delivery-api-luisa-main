package com.deliverytech.delivery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deliverytech.delivery.dto.ProductDTO;
import com.deliverytech.delivery.service.IProductService;

@RestController 
@RequestMapping("/api/v1/restaurants/products")
public class ProductController {

    @Autowired
    private IProductService productService;
 
    @GetMapping
    public ResponseEntity<List<ProductDTO>> list() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.FOUND).body(products);
    }
}

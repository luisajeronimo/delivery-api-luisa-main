package com.deliverytech.delivery.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.delivery.dto.ProductDTO;

public interface IProductService {

    List<ProductDTO> getAllProducts();
    ProductDTO createProduct(ProductDTO productDTO);
}

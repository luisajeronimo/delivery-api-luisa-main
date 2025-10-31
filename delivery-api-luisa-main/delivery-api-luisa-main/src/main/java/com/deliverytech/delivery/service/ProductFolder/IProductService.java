package com.deliverytech.delivery.service.ProductFolder;

import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.entity.ProductFolder.Product;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {

    ProductResponseDTO createProduct(ProductDTO dto);

    ProductResponseDTO updateProduct(Long id, ProductDTO dto);

    void deleteProduct(Long id);

    List<ProductResponseDTO> getAllProductsByRestaurant(Restaurant restaurant);

    List<ProductResponseDTO> getAllProductsByCategory(String category);

    List<ProductResponseDTO> getAllProductsByNameSearch(String search);

    List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    List<ProductResponseDTO> getAllProductsByMaxPrice(BigDecimal maxPrice);

    ProductResponseDTO changeProductStatus(ProductDTO productDTO);


}
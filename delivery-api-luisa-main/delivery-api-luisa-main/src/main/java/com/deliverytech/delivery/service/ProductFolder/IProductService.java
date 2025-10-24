package com.deliverytech.delivery.service.ProductFolder;

import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface IProductService {
    
    List<ProductResponseDTO> getProductsByRestaurantId(Long restaurantId);
    ProductResponseDTO createProduct(ProductDTO productDTO, Long restaurantId);
    List<ProductResponseDTO> getAllProductsByRestaurant(Restaurant restaurant);
    List<ProductResponseDTO> getAllProductsByCategory(String category);
    List<ProductResponseDTO> getAllProductsByNameSearch(String search);
    List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    List<ProductResponseDTO> getAllProductsByMaxPrice(BigDecimal maxPrice);
    ProductResponseDTO alterProductStatus(ProductDTO productDTO);
    ProductResponseDTO updateProduct(Long productId, ProductDTO productDTO);
    void deleteProduct(Long productId);
}
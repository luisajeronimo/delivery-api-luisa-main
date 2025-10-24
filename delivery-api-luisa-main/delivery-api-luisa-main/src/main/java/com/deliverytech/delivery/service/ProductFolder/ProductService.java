package com.deliverytech.delivery.service.ProductFolder;

import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.entity.ProductFolder.Product;
import com.deliverytech.delivery.entity.ProductFolder.ProductStatus;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;
import com.deliverytech.delivery.repository.ProductFolder.IProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private IProductRepository productRepository;

    @Override
    public List<ProductResponseDTO> getProductsByRestaurantId(Long restaurantId) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = productRepository.findAllByRestaurant_IdAndIsActive(restaurantId, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public ProductResponseDTO createProduct(ProductDTO productDTO, Long restaurantId) {
        ModelMapper modelMapper = new ModelMapper();
        Product entity = modelMapper.map(productDTO, Product.class);
        if (entity.getRestaurant() == null) {
            Restaurant r = new Restaurant();
            r.setId(restaurantId);
            entity.setRestaurant(r);
        } else {
            entity.getRestaurant().setId(restaurantId);
        }
        Product product = productRepository.save(entity);
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByRestaurant(Restaurant restaurant) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = productRepository.findAllByRestaurantAndIsActive(restaurant, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByCategory(String category) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = productRepository.findAllByCategoryAndIsActive(category, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByNameSearch(String search) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndIsActive(search, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = productRepository.findByPriceBetweenAndIsActive(minPrice, maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByMaxPrice(BigDecimal maxPrice) {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = productRepository.findByPriceIsLessThanEqualAndIsActive(maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public ProductResponseDTO alterProductStatus(ProductDTO productDTO) {
        return null;
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductDTO productDTO) {
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {

    }
}
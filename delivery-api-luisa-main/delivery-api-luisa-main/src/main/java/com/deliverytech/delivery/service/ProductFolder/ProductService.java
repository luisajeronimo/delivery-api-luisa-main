package com.deliverytech.delivery.service.ProductFolder;

import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.entity.ProductFolder.Product;
import com.deliverytech.delivery.entity.ProductFolder.ProductStatus;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;
import com.deliverytech.delivery.repository.ProductFolder.IProductRepository;
import com.deliverytech.delivery.repository.RestaurantFolder.IRestaurantRepository;
import com.deliverytech.delivery.config.exception.BusinessException;
import com.deliverytech.delivery.config.exception.EntityNotFoundException;
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

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductResponseDTO createProduct(ProductDTO dto) {
        // Validate restaurant exists
        if (dto.getRestaurantId() == null || !restaurantRepository.existsById(dto.getRestaurantId())) {
            throw new EntityNotFoundException("Restaurant not found: " + dto.getRestaurantId());
        }

        // check duplicate by name
        if (!productRepository.findByNameContainingIgnoreCase(dto.getName()).isEmpty()) {
            throw new BusinessException("Product with same name already exists: " + dto.getName());
        }

        Product entity = modelMapper.map(dto, Product.class);
        if (entity.getRestaurant() == null) {
            Restaurant r = new Restaurant();
            r.setId(dto.getRestaurantId());
            entity.setRestaurant(r);
        } else {
            entity.getRestaurant().setId(dto.getRestaurantId());
        }
        Product product = productRepository.save(entity);
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByRestaurant(Restaurant restaurant) {
        List<Product> products = productRepository.findByRestaurantAndStatus(restaurant, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryAndStatus(category, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByNameSearch(String search) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndStatus(search, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetweenAndStatus(minPrice, maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public List<ProductResponseDTO> getAllProductsByMaxPrice(BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceIsLessThanEqualAndStatus(maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    public ProductResponseDTO changeProductStatus(ProductDTO productDTO) {
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
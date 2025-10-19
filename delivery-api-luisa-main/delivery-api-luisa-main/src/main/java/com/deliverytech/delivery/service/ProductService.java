package com.deliverytech.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.deliverytech.delivery.dto.ProductDTO;
import com.deliverytech.delivery.dto.RestaurantDTO;
import com.deliverytech.delivery.repository.IProductRepository;
import com.deliverytech.delivery.entity.Product;
import com.deliverytech.delivery.entity.Restaurant;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository repository;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        ModelMapper mapper = new ModelMapper();

        Product entity = mapper.map(productDTO, Product.class);
        Product product = repository.save(entity);
        ProductDTO dto = mapper.map(product, ProductDTO.class);
        return dto;
    }

    @Override
        public List<ProductDTO> getAllProducts() {
            ModelMapper mapper = new ModelMapper();  
            List<Product> products = repository.findAll();
    
            List<ProductDTO> productDtoList = Arrays.asList(mapper.map(products, ProductDTO[].class));
    
            return productDtoList;
            //return repository.findAll().stream().map(this::ConvertEntityToDto).collect(Collectors.toList());
        }
}
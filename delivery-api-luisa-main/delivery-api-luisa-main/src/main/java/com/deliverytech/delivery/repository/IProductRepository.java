package com.deliverytech.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery.entity.Product;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    public Product findByName(String name);
    public boolean existsByName(String name);
}

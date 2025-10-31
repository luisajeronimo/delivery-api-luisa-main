package com.deliverytech.delivery.repository.ProductFolder;

import com.deliverytech.delivery.entity.ProductFolder.Product;
import com.deliverytech.delivery.entity.ProductFolder.ProductStatus;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface IProductRepository extends JpaRepository<Product, Long> {
    /**
     * Repositório JPA para operações sobre {@link com.deliverytech.delivery.entity.ProductFolder.Product}.
     *
     * Notas:
     * - Os métodos abaixo usam convenções do Spring Data para gerar consultas automaticamente com base
     *   nos nomes dos métodos e nos nomes dos atributos das entidades.
     */

    // Buscar produtos por restaurante
    List<Product> findByRestaurantAndStatus(Restaurant restaurant, ProductStatus status);

    // Buscar produtos por restaurante ID
    List<Product> findByRestaurantIdAndStatus(Long restaurantId, ProductStatus status);

    // Buscar por categoria
    List<Product> findByCategoryAndStatus(String categoria, ProductStatus status);
 
    // Buscar por nome contendo
    List<Product> findByNameContainingIgnoreCase(String nome);
    List<Product> findByNameContainingIgnoreCaseAndStatus(String nome, ProductStatus status);

    // Buscar por faixa de preço
    List<Product> findByPriceBetweenAndStatus(BigDecimal priceMin, BigDecimal priceMax, ProductStatus status);
 
    // Buscar produtos mais baratos que um valor
    List<Product> findByPriceIsLessThanEqualAndStatus(BigDecimal price, ProductStatus status);

    // Ordenar por preço
    List<Product> findByStatusOrderByPriceAsc(ProductStatus status);
    List<Product> findByStatusOrderByPriceDesc(ProductStatus status);
}
package com.deliverytech.delivery.service.ProductFolder;

import com.deliverytech.delivery.dto.ProductFolder.ProductDTO;
import com.deliverytech.delivery.dto.ProductFolder.ProductResponseDTO;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interface de serviço para operações relacionadas a produtos (Product).
 *
 * Responsabilidades:
 * - Definir contratos para criação, atualização, remoção e consultas de produtos.
 * - Fornecer métodos que retornam DTOs de resposta para uso pelos controllers.
 *
 * Observações:
 * - Implementações devem validar regras de negócio (disponibilidade, vínculo com restaurante,
 *   limites de preço, etc.) e lançar exceções tratadas pelo GlobalExceptionHandler quando necessário.
 */
public interface IProductService {

    /**
     * Cria um novo produto a partir do DTO.
     */
    ProductResponseDTO createProduct(ProductDTO dto);

    /**
     * Atualiza um produto existente identificado por id.
     */
    ProductResponseDTO updateProduct(Long id, ProductDTO dto);

    /**
     * Remove um produto pelo id.
     */
    void deleteProduct(Long id);

    /**
     * Retorna todos os produtos de um restaurante (filtrados por disponibilidade internamente).
     */
    List<ProductResponseDTO> getAllProductsByRestaurant(Restaurant restaurant);

    /**
     * Retorna produtos por categoria.
     */
    List<ProductResponseDTO> getAllProductsByCategory(String category);

    /**
     * Busca produtos por nome (busca parcial, case-insensitive).
     */
    List<ProductResponseDTO> getAllProductsByNameSearch(String search);

    /**
     * Busca produtos por faixa de preço.
     */
    List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Retorna produtos com preço menor ou igual a um valor máximo.
     */
    List<ProductResponseDTO> getAllProductsByMaxPrice(BigDecimal maxPrice);

    /**
     * Recupera um produto por id.
     */
    ProductResponseDTO getProductById(Long id);

    /**
     * Altera o status/disponibilidade de um produto.
     */
    ProductResponseDTO changeProductStatus(Long id, ProductDTO productDTO);

}
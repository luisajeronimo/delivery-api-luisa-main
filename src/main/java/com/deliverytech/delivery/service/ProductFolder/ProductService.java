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
/**
 * Serviço responsável pelas operações de Produto.
 *
 * Responsabilidades principais:
 * - Criação, atualização, busca e remoção de produtos.
 * - Listagens filtradas por restaurante, categoria, nome e faixa de preço.
 *
 * Observações:
 * - Este serviço mapeia entidades para DTOs usando ModelMapper.
 * - Validações e lançamentos de exceções específicas ocorrem conforme regras de negócio
 *   (por exemplo {@link EntityNotFoundException} e {@link BusinessException}).
 */
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IRestaurantRepository restaurantRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    /**
     * Cria um novo produto associado a um restaurante.
     * Valida existência do restaurante e verificação de duplicidade por nome.
     *
     * @param dto DTO com dados do produto a ser criado
     * @return DTO de resposta contendo o produto persistido
     */
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

    // Evita atualizar um produto existente por acidente: garantir que id seja nulo
    entity.setId(null);

    // Use referência gerenciada do restaurante para associar sem inserir um novo restaurante
    Restaurant restaurantRef = restaurantRepository.getReferenceById(dto.getRestaurantId());
    entity.setRestaurant(restaurantRef);

    Product registeredEntity = productRepository.save(entity);
    return modelMapper.map(registeredEntity, ProductResponseDTO.class);
    }

    @Override
    /**
     * Lista produtos disponíveis para o restaurante informado.
     *
     * @param restaurant entidade do restaurante (pode conter apenas o id)
     * @return lista de ProductResponseDTO de produtos disponíveis
     */
    public List<ProductResponseDTO> getAllProductsByRestaurant(Restaurant restaurant) {
        List<Product> products = productRepository.findByRestaurantAndStatus(restaurant, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    /**
     * Lista produtos por categoria que estejam disponíveis.
     *
     * @param category nome da categoria
     * @return lista de ProductResponseDTO
     */
    public List<ProductResponseDTO> getAllProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategoryAndStatus(category, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    /**
     * Busca produtos cujo nome contenha a string informada (case-insensitive)
     * e que estejam disponíveis.
     *
     * @param search termo de busca no nome
     * @return lista de ProductResponseDTO
     */
    public List<ProductResponseDTO> getAllProductsByNameSearch(String search) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseAndStatus(search, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    /**
     * Lista produtos dentro de uma faixa de preço (inclusiva) e disponíveis.
     *
     * @param minPrice preço mínimo
     * @param maxPrice preço máximo
     * @return lista de ProductResponseDTO
     */
    public List<ProductResponseDTO> getAllProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceBetweenAndStatus(minPrice, maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    /**
     * Lista produtos com preço menor ou igual ao informado e disponíveis.
     *
     * @param maxPrice preço máximo
     * @return lista de ProductResponseDTO
     */
    public List<ProductResponseDTO> getAllProductsByMaxPrice(BigDecimal maxPrice) {
        List<Product> products = productRepository.findByPriceIsLessThanEqualAndStatus(maxPrice, ProductStatus.AVAILABLE);
        return Arrays.asList(modelMapper.map(products, ProductResponseDTO[].class));
    }

    @Override
    /**
     * Ativa ou desativa um produto (dependendo dos campos em productDTO).
     * Implementação deve carregar a entidade, aplicar alteração de status e persistir.
     *
     * @param id identificador do produto
     * @param productDTO DTO contendo campos a alterar (por exemplo, status)
     * @return ProductResponseDTO atualizado
     */
    public ProductResponseDTO changeProductStatus(Long id, ProductDTO productDTO) {
        return null;
    }

    @Override
    /**
     * Atualiza os dados de um produto existente.
     *
     * @param productId id do produto
     * @param productDTO DTO com novos valores
     * @return ProductResponseDTO após atualização
     */
    public ProductResponseDTO updateProduct(Long productId, ProductDTO productDTO) {
        return null;
    }

    @Override
    /**
     * Remove um produto (pode ser remoção física ou lógica, conforme implementação).
     *
     * @param productId id do produto a ser removido
     */
    public void deleteProduct(Long productId) {    }

    @Override
    /**
     * Recupera um produto por id e retorna o DTO de resposta.
     *
     * @param id identificador do produto
     * @return ProductResponseDTO
     */
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return modelMapper.map(product, ProductResponseDTO.class);
    }
}
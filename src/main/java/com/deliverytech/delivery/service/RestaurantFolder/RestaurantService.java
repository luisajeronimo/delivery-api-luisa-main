package com.deliverytech.delivery.service.RestaurantFolder;
 
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantDTO;
import com.deliverytech.delivery.dto.RestaurantFolder.RestaurantResponseDTO;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;
import com.deliverytech.delivery.repository.RestaurantFolder.IRestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Service responsável pelas regras de negócio de Restaurantes.
 * Realiza o mapeamento entre DTOs e Entidades e orquestra o acesso ao repositório.
 */
@Service
public class RestaurantService implements IRestaurantService {

    @Autowired
    private IRestaurantRepository restaurantRepository;

     // ModelMapper injetado como bean para reaproveitar a instância em toda a aplicação
    @Autowired
    private ModelMapper modelMapper;

    /**
     * Lista todos os restaurantes.
     * Transação somente leitura para melhor performance.
     * Retorna a lista mapeada de entidades para DTOs de resposta.
     */
    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponseDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return Arrays.asList(modelMapper.map(restaurants, RestaurantResponseDTO[].class));
    }

    /**
     * Cria um novo restaurante.
     * 1) Mapeia o DTO recebido para entidade JPA
     * 2) Persiste no banco via repositório
     * 3) Mapeia a entidade salva (com ID gerado) para DTO de resposta
     */
    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant entity = modelMapper.map(restaurantDTO, Restaurant.class);
        Restaurant restaurant = restaurantRepository.save(entity);
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    /**
     * Atualiza um restaurante existente pelo ID.
     * 1) Busca a entidade; se não existir, retorna null (pode ser ajustado para lançar exceção)
     * 2) Aplica os dados do DTO na entidade existente
     * 3) Salva e retorna o DTO de resposta atualizado
     */
    @Transactional
    public RestaurantResponseDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant == null) {
            return null;
        }
        modelMapper.map(restaurantDTO, restaurant);
        restaurant = restaurantRepository.save(restaurant);
        return modelMapper.map(restaurant, RestaurantResponseDTO.class);
    }

    /**
     * Busca um restaurante pelo ID.
     * Retorna o DTO de resposta ou null caso não encontrado.
     */
    @Override
    public RestaurantResponseDTO getRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).map(restaurant -> modelMapper.map(restaurant, RestaurantResponseDTO.class)).orElse(null);
    }

    /**
     * Exclui um restaurante pelo ID.
     * Não retorna conteúdo (uso típico com HTTP 204).
     */
    @Override
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }
}
package com.deliverytech.delivery.repository.RestaurantFolder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.deliverytech.delivery.entity.RestaurantFolder.Restaurant;

@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {
    
    // Buscar por nome
    public Restaurant findByName(String name);
    
    //Verifica existencia
    public boolean existsByName(String name);
 
    List<Restaurant> findByCEPContainingOrderByDataCadastroDesc(String cep);
}
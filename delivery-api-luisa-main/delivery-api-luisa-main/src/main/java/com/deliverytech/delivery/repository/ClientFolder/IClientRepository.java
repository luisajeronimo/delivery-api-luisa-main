package com.deliverytech.delivery.repository.ClientFolder;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery.entity.ClientFolder.Client;

public interface IClientRepository extends JpaRepository<Client, Long> {
    
    Client findClientByEmail(String email);

    boolean existsByEmail(String email);
}

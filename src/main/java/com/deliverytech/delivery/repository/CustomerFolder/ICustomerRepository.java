package com.deliverytech.delivery.repository.CustomerFolder;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery.entity.CustomerFolder.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    // Buscar customer por email (método derivado)
    Optional<Customer> findCustomerByEmail(String email);
 
    // Verificar se email já existe
    boolean existsByEmail(String email);
 
    // Buscar customeres ativos
    List<Customer> findByActiveTrue();
 
    // Buscar customeres por nome (contendo)
    List<Customer> findByNameContainingIgnoreCase(String name);
 
    // Buscar customeres por telefone
    Optional<Customer> findByPhone(String phone);
}

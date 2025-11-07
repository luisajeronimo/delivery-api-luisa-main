package com.deliverytech.delivery.repository.UserFolder;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery.entity.UserFolder.User;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    
    public Optional<User> findByEmail(String email);
    public boolean existsByEmail(String email);
    public Optional<User> findByActiveAndEmail(Boolean active, String email);
}
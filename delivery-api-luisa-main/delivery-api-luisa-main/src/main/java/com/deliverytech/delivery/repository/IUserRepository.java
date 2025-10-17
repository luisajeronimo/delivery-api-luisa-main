package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
}

package com.deliverytech.delivery.entity.CustomerFolder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.deliverytech.delivery.entity.OrderFolder.Order;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    @Column(nullable = false, name = "customer_name")
    private String name;

    @Column(nullable = false, name = "customer_email")
    private String email;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, name = "customer_phone")
    private String phone;

    private String image;

    @Column(name = "customer_rating")
    private String rating;

    private boolean active;

    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
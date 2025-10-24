package com.deliverytech.delivery.entity.ClientFolder;

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
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(nullable = false, name = "client_name")
    private String name;

    @Column(nullable = false, name = "client_email")
    private String email;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, name = "client_phone")
    private String phone;

    private String image;

    @Column(name = "client_rating")
    private String rating;

    private boolean active;

    @Column(name = "created_at", nullable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
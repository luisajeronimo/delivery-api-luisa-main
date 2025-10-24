package com.deliverytech.delivery.entity.RestaurantFolder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.deliverytech.delivery.utils.WorkHours;

import com.deliverytech.delivery.entity.ProductFolder.Product;
import com.deliverytech.delivery.entity.OrderFolder.Order;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    @Column(name = "restaurant_name", nullable = false, length = 50)
    private String name;

    @Column(name = "restaurant_description")
    private String description;

    @Column(length = 25)
    private String cuisine;

    @Column(name = "restaurant_rating")
    private BigDecimal rating;

    private boolean active;
    private String image;

    @Column(length = 8, nullable = false)
    private String cep;

    @Column(name = "restaurant_address", length = 200)
    private String address;

    @Column(nullable = false, length = 14)
    private String cnpj;

    @Column(name = "restaurant_phone", length = 10)
    private String phone;

    @ElementCollection
    @CollectionTable(name = "work_hours", joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyColumn(name = "day_of_week")
    @MapKeyEnumerated(EnumType.STRING)
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "start_time")),
            @AttributeOverride(name = "end", column = @Column(name = "end_time"))
    })
    private Map<DayOfWeek, WorkHours> workHours;

    @OneToMany(mappedBy = "restaurant")
    private List<Product> products;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
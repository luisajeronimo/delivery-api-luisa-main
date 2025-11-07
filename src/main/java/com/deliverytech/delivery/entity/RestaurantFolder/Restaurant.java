package com.deliverytech.delivery.entity.RestaurantFolder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.deliverytech.delivery.entity.ProductFolder.Product;
import com.deliverytech.delivery.config.utils.WorkHours;
import com.deliverytech.delivery.entity.OrderFolder.Order;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Entidade JPA que representa um Restaurante.
 * Mapeada para a tabela "restaurant".
 * Usa Lombok para gerar getters/setters, construtores e equals/hashCode/toString.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant {

    // Chave primária autoincremento (IDENTITY) => coluna "restaurant_id"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;

    // Nome do restaurante (obrigatório, até 50 chars) => "restaurant_name"
    @Column(name = "restaurant_name", nullable = false, length = 50)
    private String name;

    // Taxa de entrega => "delivery_fee"
    @Column(name = "delivery_fee")
    private BigDecimal deliveryFee;

    // Descrição livre do restaurante => "restaurant_description"
    @Column(name = "restaurant_description")
    private String description;

    // Tipo de cozinha (ex.: Pizza, Japonesa) => "category"
    @Column(length = 25)
    private String category;

    // Avaliação média (ex.: 4.5) => "restaurant_rating"
    @Column(name = "restaurant_rating")
    private BigDecimal rating;

    // Indica se o restaurante está ativo para pedidos
    private boolean active;

    // URL/caminho de imagem/logo do restaurante
    private String image;

    // CEP (obrigatório, 8 chars) => "cep"
    @Column(length = 8, nullable = false)
    private String cep;

    // Endereço formatado (rua, número, etc.) => "restaurant_address"
    @Column(name = "restaurant_address", length = 200)
    private String address;

    // CNPJ (obrigatório, 14 chars) => "cnpj"
    @Column(nullable = false, length = 14)
    private String cnpj;

    // Telefone (até 10 chars) => "restaurant_phone"
    @Column(name = "restaurant_phone", length = 11)
    private String phone;

    /**
     * Horário de funcionamento por dia da semana.
     * - Armazenado em tabela própria "work_hours", ligada por FK "restaurant_id"
     * - A chave do mapa é o DayOfWeek (armazenado como STRING em "day_of_week")
     * - O valor é um embutido WorkHours com colunas sobrescritas:
     *   - start => "start_time"
     *   - end   => "end_time"
     */
    @ElementCollection
    @CollectionTable(name = "work_hours", joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyColumn(name = "day_of_week")
    @MapKeyEnumerated(EnumType.STRING)
    @AttributeOverrides({
            @AttributeOverride(name = "start", column = @Column(name = "start_time")),
            @AttributeOverride(name = "end", column = @Column(name = "end_time"))
    })
    private Map<DayOfWeek, WorkHours> workHours;

    // Relação 1:N com produtos; mapeado pelo campo "restaurant" na entidade Product
    @OneToMany(mappedBy = "restaurant")
    private List<Product> products;

    // Relação 1:N com pedidos; mapeado pelo campo "restaurant" na entidade Order
    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

    // Data/hora de criação do registro, padrão = agora => "created_at"
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
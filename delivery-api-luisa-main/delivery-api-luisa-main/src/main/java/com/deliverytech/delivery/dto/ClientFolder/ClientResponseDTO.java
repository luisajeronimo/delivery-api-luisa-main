package com.deliverytech.delivery.dto.ClientFolder;

import lombok.Data;

@Data
public class ClientResponseDTO {
    
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String cpf;
    private String image;
    private String rating;
    private boolean active;
}

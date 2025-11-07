package com.deliverytech.delivery.dto.UserFolder;

import com.deliverytech.delivery.entity.UserFolder.UserRole;

public record RegisterDTO(String email, String password, String name, UserRole role) {

}

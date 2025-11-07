package com.deliverytech.delivery.entity.UserFolder;

public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    RESTAURANT("restaurant");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
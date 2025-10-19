package com.deliverytech.delivery.entity;

public enum StatusProduct {

    AVAILABLE("admin"),
    UNAVAILABLE("user"),
    SOON ("soon"),
    INTERRUPTED("interrupted");

    private String status;
        StatusProduct(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}

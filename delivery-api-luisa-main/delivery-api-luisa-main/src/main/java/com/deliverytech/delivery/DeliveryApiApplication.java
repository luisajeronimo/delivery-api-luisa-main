package com.deliverytech.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;	

@SpringBootApplication
@SecurityScheme(
	name = "bearerAuth",
	scheme = "bearer",
	bearerFormat = "JWT",
	type = SecuritySchemeType.HTTP,
	in = SecuritySchemeIn.HEADER
)
@SecurityRequirement(name = "bearerAuth")

public class DeliveryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryApiApplication.class, args);
	}

}

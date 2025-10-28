package com.deliverytech.delivery.controller;
 
import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health Check", description = "Health Check API")
public class HealthController {

    @Operation(
            summary = "Health Check API",
            description = "Retorna informações básicas como status, timestamp, versão do Java e nome do serviço.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "API em funcionamento",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        return Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now().toString(),
            "service", "Delivery API",
            "javaVersion", System.getProperty("java.version")
        );
    }

    @Operation(
            summary = "Informações da aplicação",
            description = "Retorna informações detalhadas da aplicação como nome, versão, desenvolvedor e frameworks.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Informações da aplicação retornadas com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppInfo.class)
                            )
                    )
            }
    )

    @GetMapping("/info")
    public AppInfo info() {
        return new AppInfo(
            "Delivery Tech API",
            "1.0.0",
            "Luisa Jeronimo de Lima",
            "JDK 21",
            "Spring Boot 3.3.5"
        );
    }

    public record AppInfo(
    String name,
    String version,
    String author,
    String javaVersion,
    String framework
) {}
}
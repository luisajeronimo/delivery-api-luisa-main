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
/**
 * HealthController
 *
 * Responsabilidade:
 * - Expor endpoints simples para checagem de integridade (/health) e informações da aplicação (/info).
 *
 * Observações:
 * - Esses endpoints devem ser simples, sem dependências externas, para que a plataforma de hospedagem
 *   possa realizar health checks com baixo custo.
 */
public class HealthController {

    @Operation(
            summary = "Health Check API",
            description = "Return basic information like status, timestamp, Java version and service name.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "API is running",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )

    @GetMapping("/health")
        /**
         * GET /health
         * Retorna um mapa simples com status e timestamp para checagem de saúde.
         *
         * @return mapa contendo status, timestamp, nome do serviço e versão do Java
         */
        public Map<String, String> healthCheck() {
        return Map.of(
            "status", "UP",
            "timestamp", LocalDateTime.now().toString(),
            "service", "Delivery API",
            "javaVersion", System.getProperty("java.version")
        );
    }

    @Operation(
            summary = "Application Information",
            description = "Return detailed information about the application such as name, version, author, and frameworks.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Application information returned successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppInfo.class)
                            )
                    )
            }
    )

    @GetMapping("/info")
        /**
         * GET /info
         * Retorna informações descritivas da aplicação (nome, versão, autor e stack).
         *
         * @return {@link AppInfo} com metadados da aplicação
         */
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
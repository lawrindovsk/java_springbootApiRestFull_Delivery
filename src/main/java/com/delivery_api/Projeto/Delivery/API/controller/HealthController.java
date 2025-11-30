package com.delivery_api.Projeto.Delivery.API.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Tag(name = "Monitoramento", description = "Endpoints para verificar a saúde da aplicação")
@SecurityRequirement(name = "bearer-key") // No momento, exige token pois não está no permitAll
public class HealthController {

    @GetMapping("/health")
    @Operation(summary = "Status da API", description = "Verifica se a aplicação está no ar (Health Check).")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString(),
                "service", "Delivery API",
                "javaVersion", System.getProperty("java.version")
        );
    }

    @GetMapping("/info")
    @Operation(summary = "Informações da API", description = "Retorna versão e detalhes do desenvolvedor.")
    public AppInfo info() {
        return new AppInfo(
                "Delivery Tech API",
                "1.0.0",
                "Gustavo Laurindo",
                "JDK 21",
                "Spring Boot 3"
        );
    }

    public record AppInfo(
            String application,
            String version,
            String developer,
            String javaVersion,
            String framework
    ) {}
}
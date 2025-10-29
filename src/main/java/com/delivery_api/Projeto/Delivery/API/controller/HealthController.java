package com.delivery_api.Projeto.Delivery.API.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public Map<String, String> health(){
        return Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString(),
                "Service", "Delevery API"
        );
    }
}

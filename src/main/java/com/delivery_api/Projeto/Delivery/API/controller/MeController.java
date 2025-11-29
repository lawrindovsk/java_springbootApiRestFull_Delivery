package com.delivery_api.Projeto.Delivery.API.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/me")
@SecurityRequirement(name = "bearer-key")
public class MeController {

    @GetMapping
    public Map<String, Object> quemSouEu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // ISSO VAI NOS DIZER EXATAMENTE O QUE O SPRING ESTÁ VENDO
        return Map.of(
                "usuario", auth.getName(),
                "autoridades_que_o_spring_ve", auth.getAuthorities()
        );
    }
}
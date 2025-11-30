package com.delivery_api.Projeto.Delivery.API.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/me")
@SecurityRequirement(name = "bearer-key") // Exige token
@Tag(name = "Usuário Logado", description = "Informações sobre o usuário autenticado atual")
public class MeController {

    @GetMapping
    @Operation(summary = "Quem sou eu?", description = "Retorna os dados do usuário dono do Token (Email e Permissões).")
    @ApiResponse(responseCode = "200", description = "Dados retornados com sucesso")
    public Map<String, Object> quemSouEu() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return Map.of(
                "usuario", auth.getName(),
                "autoridades", auth.getAuthorities()
        );
    }
}
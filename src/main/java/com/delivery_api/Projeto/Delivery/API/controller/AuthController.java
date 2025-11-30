package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.LoginDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import com.delivery_api.Projeto.Delivery.API.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@Tag(name = "Autenticação", description = "Endpoint público para login e obtenção de token")
public class AuthController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    @Operation(summary = "Realizar Login", description = "Autentica usuário e retorna um Bearer Token JWT válido por 24h.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso (Token retornado)"),
            @ApiResponse(responseCode = "403", description = "Credenciais inválidas")
    })
    public String login(@RequestBody LoginDTO dados) {
        var tokenAutenticacao = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        Authentication autenticacao = manager.authenticate(tokenAutenticacao);
        return tokenService.gerarToken((Usuario) autenticacao.getPrincipal());
    }
}
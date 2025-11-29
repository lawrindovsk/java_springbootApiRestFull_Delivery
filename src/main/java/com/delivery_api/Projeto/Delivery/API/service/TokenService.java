package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class TokenService {

    // Chave secreta para assinar o token (deve ser complexa)
    private static final String SECRET = "secreta_super_secreta_123456789_delivery_api_2025";

    // Transforma a string em uma chave criptográfica
    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String gerarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail()) // Quem é o dono do token
                .claim("id", usuario.getId())   // Guardamos o ID
                .claim("role", usuario.getRole().name()) // Guardamos o perfil (ADMIN, CLIENTE)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusHours(24).atZone(ZoneId.systemDefault()).toInstant())) // Expira em 24h
                .signWith(getKey(), SignatureAlgorithm.HS256) // Assina digitalmente
                .compact();
    }

    // Valida se o token é autêntico e não expirou
    public String validarToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // Retorna o email se estiver tudo ok
        } catch (Exception e) {
            return null; // Token inválido
        }
    }
}
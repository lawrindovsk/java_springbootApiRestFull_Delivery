package com.delivery_api.Projeto.Delivery.API.infra.security;

import com.delivery_api.Projeto.Delivery.API.repository.UsuarioRepository;
import com.delivery_api.Projeto.Delivery.API.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository repository;

    public SecurityFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token
        var token = recuperarToken(request); // <--- AGORA VAI FUNCIONAR POIS O MÉTODO ESTÁ LÁ EMBAIXO

        if (token != null) {
            var subject = tokenService.validarToken(token);
            System.out.println(">>> [FILTER] Token recebido. Subject (Email): " + subject); // LOG 1

            if (subject != null) {
                UserDetails usuario = repository.findByEmail(subject);

                // LOGS IMPORTANTES PARA DEBUGAR O 403
                System.out.println(">>> [FILTER] Usuario encontrado: " + usuario.getUsername());
                System.out.println(">>> [FILTER] Permissões (Roles): " + usuario.getAuthorities());

                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println(">>> [FILTER] Token inválido ou expirado!");
            }
        } else {
            System.out.println(">>> [FILTER] Sem token na requisição.");
        }

        filterChain.doFilter(request, response);
    }

    // --- ESTE É O MÉTODO QUE ESTAVA FALTANDO ---
    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}
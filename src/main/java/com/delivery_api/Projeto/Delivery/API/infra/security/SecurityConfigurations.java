package com.delivery_api.Projeto.Delivery.API.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita proteção contra ataques CSRF (padrão em APIs REST)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Diz que não vamos usar sessão (cookies), vamos usar Token
                .authorizeHttpRequests(req -> {
                    // Libera H2 Console
                    req.requestMatchers("/h2-console/**").permitAll();

                    // LIBERA DOCUMENTAÇÃO (Esses são os endereços mágicos do Swagger)
                    req.requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html"
                    ).permitAll();

                    // Libera Login e Cadastro (Endpoints públicos)
                    req.requestMatchers(HttpMethod.POST, "/api/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll();

                    // Todo o resto precisa de autenticação
                    req.anyRequest().authenticated();
                })
                // Configuração necessária para o H2 Console funcionar
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .build();
    }

    // Serve para podermos injetar o "Gerente de Autenticação" no Controller depois
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Algoritmo de hash de senha (para não salvar senha "123456" pura no banco)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
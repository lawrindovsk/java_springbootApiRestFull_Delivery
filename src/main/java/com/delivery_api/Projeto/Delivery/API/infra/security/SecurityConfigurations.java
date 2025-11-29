package com.delivery_api.Projeto.Delivery.API.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Importante


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {

    // Injeção do nosso filtro novo
    private final SecurityFilter securityFilter;

    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req -> {
                    // Rotas Públicas (H2, Swagger, Docs) - NÃO PRECISA DO "new AntPathRequestMatcher"
                    req.requestMatchers("/h2-console/**").permitAll();
                    req.requestMatchers("/v3/api-docs/**").permitAll();
                    req.requestMatchers("/swagger-ui/**").permitAll();
                    req.requestMatchers("/swagger-ui.html").permitAll();
                    req.requestMatchers("/error").permitAll();

                    // Login e Cadastro
                    req.requestMatchers(HttpMethod.POST, "/api/login").permitAll();
                    req.requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll();

                    // Bloqueia todo o resto
                    req.anyRequest().authenticated();
                })
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // --- AQUI ESTÁ A MÁGICA ---
                // Adiciona nosso filtro ANTES do filtro padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // ... O restante dos métodos (webSecurityCustomizer, authenticationManager, passwordEncoder) continua igual ...
    // Vou repetir aqui só para garantir que você não apague sem querer:

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html"
        );
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
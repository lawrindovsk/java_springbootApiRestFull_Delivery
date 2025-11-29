package com.delivery_api.Projeto.Delivery.API.repository;

import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // O Spring Security usa esse metodo parta procurar pelo usuario.
    UserDetails findByEmail(String email);
}
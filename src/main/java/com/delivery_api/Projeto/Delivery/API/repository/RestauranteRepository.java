package com.delivery_api.Projeto.Delivery.API.repository;

import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    //extends (se extende), ao banco de dados, pegando restaurante (nome) e Long (Id do restaurante).
    List<Restaurante> findByTaxaEntrega(BigDecimal taxaMaxima);
    List<Restaurante> findByNomeContaining(String nome);
    List<Restaurante> findByCategoria(String categoria);
}

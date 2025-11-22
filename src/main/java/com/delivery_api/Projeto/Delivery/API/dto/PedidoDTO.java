package com.delivery_api.Projeto.Delivery.API.dto;

import java.util.List;

// Record é uma classe imutável perfeita para DTOs (Java 16+)
public record PedidoDTO(
        Long clienteId,
        Long restauranteId,
        List<Long> produtosIds // Lista de IDs dos produtos que o cliente quer comprar
) {}
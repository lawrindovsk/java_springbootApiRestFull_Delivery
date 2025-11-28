package com.delivery_api.Projeto.Delivery.API.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record RestauranteDTO(

        @NotBlank(message = "O nome é obrigatório") // Não pode ser null nem vazio
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres")
        String nome,

        @NotBlank(message = "A categoria é obrigatória")
        String categoria,

        @DecimalMin(value = "0.00", message = "A taxa de entrega não pode ser negativa")
        BigDecimal taxaEntrega
) {}
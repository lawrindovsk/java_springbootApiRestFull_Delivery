package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.RestauranteDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.service.RestauranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Restaurantes", description = "Gerenciamento de restaurantes e cardápios")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ADMIN')")
    @Operation(summary = "Cadastrar restaurante", description = "Cria um novo restaurante. Requer permissão de ADMIN.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado (Apenas Admin)")
    })
    public ResponseEntity<Restaurante> cadastrar(@Valid @RequestBody RestauranteDTO dto) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(dto.nome());
        restaurante.setCategoria(dto.categoria());
        restaurante.setTaxaEntrega(dto.taxaEntrega());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restauranteService.salvar(restaurante));
    }

    @GetMapping
    @Operation(summary = "Listar restaurantes", description = "Retorna a lista de todos os restaurantes cadastrados.")
    public List<Restaurante> listar() {
        return restauranteService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar restaurante por ID", description = "Retorna os detalhes de um restaurante específico.")
    @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(restauranteService.buscarPorId(id));
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Filtrar por categoria", description = "Busca restaurantes de uma categoria específica (ex: Pizza, Japonês).")
    public List<Restaurante> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarPorCategoria(categoria);
    }
}
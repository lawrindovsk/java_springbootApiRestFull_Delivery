package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.entity.Produto;
import com.delivery_api.Projeto.Delivery.API.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Produtos", description = "Gerenciamento de produtos do cardápio")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping("/produtos")
    @Operation(summary = "Cadastrar produto", description = "Cria um novo produto vinculado a um restaurante existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Restaurante inválido ou dados incorretos")
    })
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtoService.salvar(produto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/produtos")
    @Operation(summary = "Listar todos os produtos", description = "Lista geral de produtos do sistema.")
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/restaurantes/{restauranteId}/produtos")
    @Operation(summary = "Cardápio do restaurante", description = "Lista todos os produtos de um restaurante específico.")
    @ApiResponse(responseCode = "200", description = "Cardápio retornado com sucesso")
    public List<Produto> buscarPorRestaurante(@PathVariable Long restauranteId) {
        return produtoService.buscarPorRestaurante(restauranteId);
    }
}
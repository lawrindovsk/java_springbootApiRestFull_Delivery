package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.entity.Produto;
import com.delivery_api.Projeto.Delivery.API.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // Note que aqui usei só /api para poder variar as rotas abaixo
@SecurityRequirement(name = "bearer-key")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // 1. Cadastrar Produto (POST /api/produtos)
    @PostMapping("/produtos")
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(produtoService.salvar(produto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build(); // Retorna 400 se o restaurante não existir
        }
    }

    // 2. Listar Todos (GET /api/produtos)
    @GetMapping("/produtos")
    public List<Produto> listarTodos() {
        return produtoService.listarTodos();
    }

    // 3. Buscar Produtos de um Restaurante Específico
    // URL: /api/restaurantes/{idRestaurante}/produtos
    @GetMapping("/restaurantes/{restauranteId}/produtos")
    public List<Produto> buscarPorRestaurante(@PathVariable Long restauranteId) {
        return produtoService.buscarPorRestaurante(restauranteId);
    }
}
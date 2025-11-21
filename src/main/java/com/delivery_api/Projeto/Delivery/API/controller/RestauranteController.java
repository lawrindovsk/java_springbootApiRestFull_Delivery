package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurantes")
public class RestauranteController {

    private final RestauranteService restauranteService;

    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    @PostMapping
    public ResponseEntity<Restaurante> cadastrar(@RequestBody Restaurante restaurante) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(restauranteService.salvar(restaurante));
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restauranteService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint extra para filtrar: /api/restaurantes/categoria/Pizza
    @GetMapping("/categoria/{categoria}")
    public List<Restaurante> buscarPorCategoria(@PathVariable String categoria) {
        return restauranteService.buscarPorCategoria(categoria);
    }
}
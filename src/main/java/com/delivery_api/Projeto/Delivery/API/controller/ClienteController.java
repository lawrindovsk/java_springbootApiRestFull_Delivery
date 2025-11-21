package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Fala pro Spring: "Isso aqui recebe requisições HTTP (JSON)"
@RequestMapping("/api/clientes") // Prefixo para todas as rotas dessa classe
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // 1. Cadastrar Cliente (POST)
    @PostMapping
    public ResponseEntity<Object> cadastrar(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.salvar(cliente);
            // Retorna 201 Created e o objeto criado
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (RuntimeException e) {
            // Se der erro de regra de negócio (email duplicado), retorna 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 2. Listar Todos (GET)
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }

    // 3. Buscar por ID (GET com parâmetro)
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clienteService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
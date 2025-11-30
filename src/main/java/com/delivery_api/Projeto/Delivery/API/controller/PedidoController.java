package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.dto.PedidoDTO;
import com.delivery_api.Projeto.Delivery.API.dto.StatusDTO;
import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import com.delivery_api.Projeto.Delivery.API.service.PedidoService;
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
@RequestMapping("/api/pedidos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos, status e histórico") // <--- NOME E DESCRIÇÃO DO GRUPO
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @Operation(summary = "Realizar um novo pedido", description = "Cria um pedido para o cliente, calculando o valor total com base nos produtos e taxa de entrega.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos (ex: cliente não existe, lista vazia)"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    public ResponseEntity<Object> criarPedido(@RequestBody PedidoDTO dto) {
        try {
            Pedido pedido = pedidoService.criarPedido(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna os detalhes completos de um pedido específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna a lista completa de pedidos do sistema.")
    public List<Pedido> listarTodos() {
        return pedidoService.listarTodos();
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Histórico de pedidos do cliente", description = "Lista todos os pedidos feitos por um cliente específico.")
    public List<Pedido> listarPorCliente(@PathVariable Long clienteId) {
        return pedidoService.listarPorCliente(clienteId);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Permite alterar o status (ex: DE ABERTO PARA EM_PREPARO).")
    public ResponseEntity<Pedido> atualizarStatus(@PathVariable Long id, @RequestBody StatusDTO dto) {
        return ResponseEntity.ok(pedidoService.atualizarStatus(id, dto.status()));
    }
}
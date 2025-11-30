package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.dto.PedidoDTO;
import com.delivery_api.Projeto.Delivery.API.entity.*;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import com.delivery_api.Projeto.Delivery.API.repository.PedidoRepository;
import com.delivery_api.Projeto.Delivery.API.repository.ProdutoRepository;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    // Injeta TODOS os repositórios pois o pedido precisa de todos eles
    public PedidoService(PedidoRepository pedidoRepository,
                         ClienteRepository clienteRepository,
                         RestauranteRepository restauranteRepository,
                         ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido criarPedido(PedidoDTO dto) {
        //Buscar e Validar Cliente
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Buscar e Validar Restaurante
        Restaurante restaurante = restauranteRepository.findById(dto.restauranteId())
                .orElseThrow(() -> new RuntimeException("Restaurante não encontrado"));

        // Buscar os Produtos
        List<Produto> produtos = produtoRepository.findAllById(dto.produtosIds());

        if (produtos.isEmpty()) {
            throw new RuntimeException("Pedido deve ter pelo menos um produto!");
        }

        // Calcular o Total
        BigDecimal total = BigDecimal.ZERO;
        for (Produto p : produtos) {
            total = total.add(p.getPreco());
        }

        // Adiciona a taxa de entrega do restaurante ao total
        total = total.add(restaurante.getTaxaEntrega());

        //Montar o Objeto Pedido
        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(cliente);
        novoPedido.setRestaurante(restaurante);
        novoPedido.setValorTotal(total);
        novoPedido.setStatus(StatusPedido.ABERTO);

        //Salvar
        return pedidoRepository.save(novoPedido);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
    public Pedido atualizarStatus(Long id, StatusPedido novoStatus){
        Pedido pedido = buscarPorId(id);

        //aqui pode ter regras.
        pedido.setStatus(novoStatus);

        return pedidoRepository.save(pedido);

    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }


}
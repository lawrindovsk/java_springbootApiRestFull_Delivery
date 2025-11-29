package com.delivery_api.Projeto.Delivery.API.repository;
import com.delivery_api.Projeto.Delivery.API.entity.Pedido;
import com.delivery_api.Projeto.Delivery.API.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByRestauranteId(Long restauranteId);
    //tipo Long, pois pode haver de ter muitos clientes, e muitos restaurantes.
}

package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRespository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRespository restauranteRespository;

    public RestauranteService(RestauranteRespository restauranteRespository){
        this.restauranteRespository = restauranteRespository;
    }

    public List<Restaurante> listarTodos() {
        return restauranteRespository.findAll();
    }

    public Restaurante salvar(Restaurante restaurante){
        if (restaurante.getTaxaEntrega() == null ){
            throw new RuntimeException("Taxa de entrega é obrigatória");
        }
        return restauranteRespository.save(restaurante);
    }
}

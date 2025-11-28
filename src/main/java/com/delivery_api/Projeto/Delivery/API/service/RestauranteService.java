package com.delivery_api.Projeto.Delivery.API.service;

import com.delivery_api.Projeto.Delivery.API.exception.EntityNotFoundException;
import com.delivery_api.Projeto.Delivery.API.entity.Restaurante;
import com.delivery_api.Projeto.Delivery.API.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    public RestauranteService(RestauranteRepository restauranteRepository){
        this.restauranteRepository = restauranteRepository;
    }
    public List<Restaurante> listarTodos() {
        return restauranteRepository.findAll();
    }

    public Restaurante salvar(Restaurante restaurante){
        if (restaurante.getTaxaEntrega() == null ){
            throw new RuntimeException("Taxa de entrega é obrigatória");
        }
        return restauranteRepository.save(restaurante);
    }
    public Restaurante buscarPorId(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurante não encontrado com o ID:" + id));
    }

    public List<Restaurante> buscarPorCategoria(String categoria) {
        // Este método usa aquele findByCategoria que criamos no Repository
        // Se der erro aqui, verifique se o RestauranteRepository tem o método findByCategoria
        // Se não tiver, vá no Repository e adicione: List<Restaurante> findByCategoria(String categoria);
        return restauranteRepository.findByCategoria(categoria); // Você vai precisar ajustar o Repository se ainda não tiver esse método simples
    }

}

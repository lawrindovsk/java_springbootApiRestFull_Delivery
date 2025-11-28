package com.delivery_api.Projeto.Delivery.API;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import com.delivery_api.Projeto.Delivery.API.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component // chamando o component, para dizer ao spring = "GERENCIE ESSA CLASSE"
public class DataLoader implements CommandLineRunner {
    @Autowired
    private final ClienteService clienteService;

    public DataLoader(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @Override
    public void run (String... args) throws Exception{
        //metodo é rodando no inicio
        //metodo strings ... args = siginica um array (lista) de texto (strings)
        //que o programa recebe como argumentos de linha de comando, permitindo que passe dados.
        System.out.println("+++ Carregando dados Teste +++");
        Cliente cliente1 = new Cliente ("Elza de Jesus", "elza.jesus@gmail.com","São Paulo", "11987654321");
        clienteService.salvar(cliente1); //jpa cria um insert no SQL.

        Cliente cliente2 = new Cliente("Gustavo Laurindo", "gustavo.laurindo.santos@gmail.com", "Diadema", "11012345789");
        clienteService.salvar(cliente2);

        System.out.println("Usuários salvos no banco.");
    }

}

package com.delivery_api.Projeto.Delivery.API;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // chamando o component, para dizer ao spring = "GERENCIE ESSA CLASSE"
public class DataLoader implements CommandLineRunner {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void run (String... args) throws Exception{
        //metodo é rodando no inicio
        //metodo strings ... args = siginica um array (lista) de texto (strings)
        //que o programa recebe como argumentos de linha de comando, permitindo que passe dados.
        System.out.println("+++ Carregando dados Teste +++");
        Cliente cliente1 = new Cliente ("Elza de Jesus", "elzaJesus@gmail.com" );
        clienteRepository.save(cliente1); //jpa cria um insert no SQL.

        Cliente cliente2 = new Cliente("Julia Maciel", "julia.maciel@gmail.com");
        clienteRepository.save(cliente2);

        System.out.println("Usuários salvos no banco.");

        Cliente clienteEncontrado = clienteRepository.findByEmail("julia.maciel");
        if (clienteEncontrado != null){
            System.out.println("Cliente encontrado por e-mail: " + clienteEncontrado.getNome());
        }

        System.out.println("+++ Teste de dados concluido +++");
    }

}

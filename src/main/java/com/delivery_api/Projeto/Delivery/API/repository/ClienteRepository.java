package com.delivery_api.Projeto.Delivery.API.repository;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente; // Importe sua entidade
import org.springframework.data.jpa.repository.JpaRepository; // Importe o JpaRepository
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //JpaRepository<Cliente, Long> siginifca que = "eu sou um repo de entidade Cleinte, e a chave é o ID, do tipo long".

    //para buscar pelo cliente, por email por ex: precisamos somente declarar o método. O spring data JPA vai ler o nome, e criar um "comando"
    //SELECT * FROM clientes WHERE email = ??
    Cliente findByEmail(String email);

    List<Cliente> findByAtivoTrue();

}

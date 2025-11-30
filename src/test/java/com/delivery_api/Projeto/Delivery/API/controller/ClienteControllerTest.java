package com.delivery_api.Projeto.Delivery.API.controller;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // Sobe a aplicação inteira
@AutoConfigureMockMvc // Configura o "Navegador Fake"
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // O "Navegador Fake"

    @Autowired
    private ObjectMapper objectMapper; // Para transformar Objeto em JSON

    @Test
    @DisplayName("Deve retornar 201 Created ao criar cliente válido")
    @WithMockUser(roles = "ADMIN") // Simula que estamos logados como Admin (Burlar Segurança)
    void deveCriarCliente() throws Exception {
        // Cria um cliente com email único (use timestamp para garantir unicidade no teste)
        Cliente cliente = new Cliente("Teste Integrado", "teste" + System.currentTimeMillis() + "@email.com", "naosei", "1140028922");
        cliente.setEndereco("Rua Teste");
        cliente.setTelefone("11999999999");

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated()); // Espera 201
    }

    @Test
    @DisplayName("Deve retornar 403 Forbidden se tentar criar sem login")
    void deveBloquearSemLogin() throws Exception {
        Cliente cliente = new Cliente("Hacker", "hacker@email.com", "alo", "40028922");

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isForbidden()); // Espera 403
    }
}
package com.delivery_api.Projeto.Delivery.API.service; // Mantenha seu pacote

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.repository.ClienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o uso de @Mock e @InjectMocks
class ClienteServiceTest {

    @Mock // Cria um "dublê" do repositório
    private ClienteRepository clienteRepository;

    @InjectMocks // Injeta o dublê dentro do service real
    private ClienteService clienteService;

    @Test
    @DisplayName("Deve salvar cliente com sucesso quando email não existe")
    void deveSalvarClienteComSucesso() {
        // ARRANGE (Preparação)
        Cliente cliente = new Cliente("João", "joao@teste.com", "diadema", "1199999999");

        // Ensinamos o Mock: "Quando buscarem por esse email, diga que não achou nada (empty)"
        when(clienteRepository.findByEmail("joao@teste.com")).thenReturn(Optional.empty());
        // Ensinamos o Mock: "Quando pedirem pra salvar, devolva o próprio cliente"
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // ACT (Ação)
        Cliente salvo = clienteService.salvar(cliente);

        // ASSERT (Verificação)
        assertNotNull(salvo);
        assertEquals("João", salvo.getNome());
        // Verifica se o método save foi chamado exatamente 1 vez
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe")
    void deveLancarExcecaoQuandoEmailDuplicado() {
        // ARRANGE
        Cliente cliente = new Cliente("Maria", "maria@teste.com", "sbc", "1199999991");

        // Ensinamos o Mock: "Quando buscarem, diga que JÁ EXISTE um cliente"
        when(clienteRepository.findByEmail("maria@teste.com")).thenReturn(Optional.of(new Cliente()));

        // ACT & ASSERT (Ação e Verificação juntas)
        // Esperamos que o código lance uma RuntimeException
        assertThrows(RuntimeException.class, () -> {
            clienteService.salvar(cliente);
        });

        // Garante que o método save NUNCA foi chamado (pois deve falhar antes)
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}
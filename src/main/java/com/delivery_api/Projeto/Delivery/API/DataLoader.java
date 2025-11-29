package com.delivery_api.Projeto.Delivery.API;

import com.delivery_api.Projeto.Delivery.API.entity.Cliente;
import com.delivery_api.Projeto.Delivery.API.entity.Usuario;
import com.delivery_api.Projeto.Delivery.API.entity.UserRole;
import com.delivery_api.Projeto.Delivery.API.repository.UsuarioRepository; // Importante
import com.delivery_api.Projeto.Delivery.API.service.ClienteService;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // Importante
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    // 1. Declaração das variáveis
    private final ClienteService clienteService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // 2. Construtor (AQUI ESTAVA O ERRO: Faltava receber os novos parâmetros)
    public DataLoader(ClienteService clienteService,
                      UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder) {
        this.clienteService = clienteService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("+++ Carregando dados de Teste +++");

        // --- Clientes ---
        try {
            Cliente c1 = new Cliente("Elza", "elza.jesus@gmail.com", "vila","11987654301");
            clienteService.salvar(c1);
        } catch (Exception e) {
            System.out.println("Cliente já existe.");
        }

        // --- Admin ---
        // Agora o findByEmail vai funcionar porque arrumamos o Repository
        if (usuarioRepository.findByEmail("root@delivery.com") == null) {
            Usuario admin = new Usuario();
            admin.setEmail("root@delivery.com");
            // Agora o passwordEncoder vai funcionar porque arrumamos o Construtor
            admin.setSenha(passwordEncoder.encode("123456"));
            admin.setRole(UserRole.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("+++ Usuario ADMIN criado: root@delivery.com / 123456");
        }
        if(usuarioRepository.findByEmail("joao@cliente.com") == null){
            Usuario joao = new Usuario();
            joao.setEmail("joao@cliente.com");
            joao.setSenha(passwordEncoder.encode("012345"));
            joao.setRole(UserRole.CLIENTE);
            usuarioRepository.save(joao);
            System.out.println("usuario cliente criado: joao@cliente.com / 012345");
        }

    }
}
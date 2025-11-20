package com.delivery_api.Projeto.Delivery.API.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private Boolean ativo = true;

    //Construtores (necessário para o JPA/DataLoader)
    // 1. Construtor Vazio (OBRIGATÓRIO para o JPA/Hibernate funcionar)
    public Cliente() {
    }

    // 2. Construtor com argumentos (Para o seu DataLoader funcionar)
    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }



    //Get e Set;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}
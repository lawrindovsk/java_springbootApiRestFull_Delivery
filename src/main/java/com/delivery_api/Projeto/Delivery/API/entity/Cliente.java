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
    private boolean ativo;

    public Cliente(){
        //JPA PRECISA DE CONSTRUTOR (MESMO ELE VAZIO)
    }

    public Cliente(String nome, String email){
        this.nome = nome;
        this.email = email;
        this.ativo = true;
    }
    public Long getID(){
        return id;
    }
    public void setID(Long id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }


    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}

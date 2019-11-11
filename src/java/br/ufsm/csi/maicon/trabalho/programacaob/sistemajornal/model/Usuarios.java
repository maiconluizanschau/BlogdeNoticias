/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model;

/**
 *
 * @author maicon
 */
public class Usuarios {
    
    private String nome;
    private String login;
    private String senha;
    private Integer ativo;
    private Integer id;
    
    
     public Usuarios(){
     }
    public Usuarios (String nome, String login, String senha, Integer ativo){
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;
    }
   
    
    public Usuarios (String nome, String login, String senha, Integer ativo, Integer id){
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;
        this.id = id;
    }

    public Integer getAtivo() {return ativo;}

    public void setAtivo(Integer ativo) {this.ativo = ativo;}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
  
}
    


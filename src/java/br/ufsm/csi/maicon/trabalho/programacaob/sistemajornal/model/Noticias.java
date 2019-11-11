/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model;

import java.util.Calendar;

/**
 *
 * @author maicon
 */
public class Noticias {
   
    Integer id;
    String titulo;
    String descricao;
    Calendar dataEhora;
    Integer usuariopubli;

    public Noticias(){}

    public Noticias(String titulo, String descricao){
        this.titulo = titulo;
        this.descricao = descricao;
    }
    public Noticias(String titulo, String descricao, Integer id){
        this.titulo = titulo;
        this.descricao = descricao;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Calendar getDataEhora() {
        return dataEhora;
    }

    public void setDataEhora(Calendar dataEhora) {
        this.dataEhora = dataEhora;
    }

    public Integer getUsuariopubli() {
        return usuariopubli;
    }

    public void setUsuariopubli(Integer usuariopubli) {
        this.usuariopubli = usuariopubli;
    }
}



package com.FF_BackForFront_Service.model;

public class Atributo {

    public Atributo(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    private String nome;
    private String valor;

    public String getNome() {
        return this.nome;
    }

    public String getValor() {
        return this.valor;
    }
}
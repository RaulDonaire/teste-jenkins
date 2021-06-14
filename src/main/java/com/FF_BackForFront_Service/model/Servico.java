package com.FF_BackForFront_Service.model;
import java.util.List;
import java.util.ArrayList;

public class Servico {
    private String nome;
    private String componenteReact;
    private String icon;
    private List<Atributo> atributos;

    public Servico(String nome, String componenteReact, String icon){
        this.nome = nome;
        this.componenteReact = componenteReact;
        this.icon = icon;
        this.atributos = new ArrayList<>();
    }

    public String getNome(){
        return this.nome;
    }

    public String getComponenteReact(){
        return this.componenteReact;
    }

    public String getIcon(){
        return this.icon;
    }

    public List<Atributo> getAtributos(){
        return this.atributos;
    }

    public void addAtributo(Atributo att){
        this.atributos.add(att);
    }
}

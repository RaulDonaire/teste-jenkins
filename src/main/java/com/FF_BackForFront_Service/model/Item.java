package com.FF_BackForFront_Service.model;
import java.util.List;
import java.util.ArrayList;


public class Item {
    

    public Item(String nome){
       this.nome = nome;
       this.servicos = new ArrayList<>();
    }

    private String nome;
    private List<Servico> servicos;

    public String getNome(){
        return this.nome;
    }

    public List<Servico> getServicos(){
        return this.servicos;
    }

    public void addServico(Servico servico){
        this.servicos.add(servico);
    }
}

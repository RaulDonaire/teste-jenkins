package com.FF_BackForFront_Service.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class MenuItems {

    public MenuItems() {
        this.items = new ArrayList<>();
        this.userName = "Placeholder";

    }

    private List<Item> items;
    private String userName;

    public void montaMenu(String token) {
        Item item1 = new Item("Receitas");
        Item item2 = new Item("Outros");
        Servico serv1 = new Servico("Bolo de Chocolate", "Receita", "cake");
        Servico serv2 = new Servico("Bolo de Morango", "Receita", "cake");

        Atributo fotoServ1 = new Atributo("srcFoto",
                "https://i.pinimg.com/736x/b1/0b/f3/b10bf36c6d6f4cc32819803c801bbec8.jpg");
        Atributo fotoServ2 = new Atributo("srcFoto",
                "https://img.cybercook.com.br/receitas/12/bolo-de-morango-3-623x350.jpeg");

        Atributo ingredienteServ1 = new Atributo("ingrediente", " Chocolate");
        Atributo ingredienteServ2 = new Atributo("ingrediente", " Morango");
        Atributo qtdBolo = new Atributo("quantidadeBolos", "5");
        Atributo isActive = new Atributo("isActive", "false");

        serv1.addAtributo(ingredienteServ1);
        serv1.addAtributo(fotoServ1);
        serv1.addAtributo(qtdBolo);
        serv1.addAtributo(isActive);
        serv2.addAtributo(ingredienteServ2);
        serv2.addAtributo(fotoServ2);
        serv2.addAtributo(qtdBolo);
        serv2.addAtributo(isActive);

        item1.addServico(serv1);
        item1.addServico(serv2);

        Servico serv3 = new Servico("Get Users", "OlaMundo", "document");
        Atributo texto = new Atributo("texto", "Buscar");
        Atributo userList = new Atributo("userlist", "");
        Atributo erro = new Atributo("erroBusca", "");

        serv3.addAtributo(texto);
        serv3.addAtributo(isActive);
        serv3.addAtributo(userList);
        serv3.addAtributo(erro);

        item2.addServico(serv3);

        try {
            String[] pedacos = token.split("\\.");
            String body = pedacos[1];

            String agoraVai = new String(Base64.getDecoder().decode(body));

            JSONObject data = new JSONObject(agoraVai);
            System.out.println(agoraVai);
            JSONObject realmAccess = data.getJSONObject("realm_access");
            JSONArray roles = realmAccess.getJSONArray("roles");

            this.setUserName(data.get("name").toString());
            this.addItem(item1);
            roles.forEach((Object role) -> {
                System.out.println(role);
                if (role.toString().equalsIgnoreCase("admin"))
                    this.addItem(item2);
            });

            System.out.println(this.getItems());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Solucao temporária para token invalido");
        }
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return this.userName;
    }

    public boolean contemServico(String nomeServico) {
        for (Item item : this.items) {
            if (item.contemServico(nomeServico))
                return true;
        }
        return false;
    }

}

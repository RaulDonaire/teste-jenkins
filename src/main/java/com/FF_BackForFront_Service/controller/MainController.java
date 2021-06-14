package com.FF_BackForFront_Service.controller;

import com.FF_BackForFront_Service.model.Mensagem;
import com.FF_BackForFront_Service.model.Atributo;
import com.FF_BackForFront_Service.model.Servico;
import com.FF_BackForFront_Service.model.Item;
import com.FF_BackForFront_Service.model.MenuItems;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.IOException;

import java.net.URI;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class MainController {

    @PostMapping("/print_users")
    public String PostHello(@RequestBody Mensagem novaMensagem) {
        IO.Options options = new IO.Options();
        options.path = "/ff-socket-server/";
        System.out.println("Entrei aqui");
        // Socket socket = IO.socket(URI.create("http://localhost:1900"));
        // Socket socket = IO.socket(URI.create("http://localhost:8070"), options);
        Socket socket = IO.socket(URI.create("http://host.docker.internal:8070"), options);
        socket.connect();
        System.out.println("conectado com o socket");
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(novaMensagem);
            socket.emit("data-collector-result", json);
            System.out.println("Enviei pelo socket: \n" + json);
            return json;
        } catch (Exception e) {
            System.out.println("Erro meu chapa");
        }

        return "Erro na requisição";
    }

    @GetMapping("/get_users")
    public String getUsers() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("RESPONSE_SERVICE_URL", "http://host.docker.internal:8070/bff/print_users");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> content = restTemplate.exchange(
                "http://host.docker.internal:8090/ff-request-bus/get_users", HttpMethod.POST, entity, String.class);

        System.out.println(content.getBody());

        return content.getBody();
    }

    @PostMapping("/consulta/{entidade}")
    public String getClient(@PathVariable String entidade, @RequestBody(required = false) List<LinkedHashMap<String,String>> body) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("RESPONSE_SERVICE_URL", "http://host.docker.internal:8070/bff/print_users");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(body);
        } catch (Exception e) {
            System.out.println("Erro meu chapa");
        }

        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        ResponseEntity<String> content = restTemplate.exchange(
                "http://host.docker.internal:8090/ff-request-bus/" + entidade, HttpMethod.POST, entity, String.class);

        System.out.println(content.getBody());

        return content.getBody();
    }


    @GetMapping("/menu")
    public MenuItems menuItem(@RequestHeader(value = "Authorization") String token) {
        MenuItems menu = new MenuItems();

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

        menu.addItem(item1);

        Servico serv3 = new Servico("Get Users", "OlaMundo", "document");
        Atributo texto = new Atributo("texto", "Buscar");
        Atributo userList = new Atributo("userlist", "");
        Atributo erro = new Atributo("erroBusca", "");

        serv3.addAtributo(texto);
        serv3.addAtributo(isActive);
        serv3.addAtributo(userList);
        serv3.addAtributo(erro);

        item2.addServico(serv3);

        String[] pedacos = token.split("\\.");
        String body = pedacos[1];

        String agoraVai = new String(Base64.getDecoder().decode(body));

        JSONObject data = new JSONObject(agoraVai);
        System.out.println(agoraVai);

        JSONObject realmAccess = data.getJSONObject("realm_access");
        JSONArray roles = realmAccess.getJSONArray("roles");

        menu.setUserName(data.get("name").toString());

        roles.forEach((Object role) -> {
            System.out.println(role);
            if (role.toString().equalsIgnoreCase("admin"))
                menu.addItem(item2);
        });

        return menu;
    }

}

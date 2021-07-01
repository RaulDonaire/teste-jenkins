package com.FF_BackForFront_Service.controller;

import com.FF_BackForFront_Service.model.Mensagem;

import com.FF_BackForFront_Service.model.MenuItems;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.io.IOException;

import java.net.URI;

import java.util.LinkedHashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/")
public class MainController {

    @Value("${caminho.request.bus}")
    String caminhoDoRequestBus;

    protected Socket createSocket() {
        IO.Options options = new IO.Options();
        options.path = "/ff-socket-server/";
        System.out.println("Entrei aqui");
        return IO.socket(URI.create("http://host.docker.internal:8070"), options);
    }

    public void setCaminhoRequestBus(String caminhoRequestBus) {
        this.caminhoDoRequestBus = caminhoRequestBus;
    }

    public String getCaminhoRequestBus() {
        return this.caminhoDoRequestBus;
    }

    @PostMapping("/print_users/request-bus/response")
    public ResponseEntity<String> PostHello(@RequestBody Mensagem novaMensagem) {

        if (novaMensagem.getName() != null && !novaMensagem.getName().isEmpty() && novaMensagem.getUuid() != null) {

            try {
                Socket socket = createSocket();
                socket.connect();
                System.out.println("conectado com o socket");
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(novaMensagem);
                System.out.println(json);
                socket.emit("data-collector-result", json);
                System.out.println("Enviei pelo socket: \n" + json);
                return ResponseEntity.ok(json);

            } catch (Exception e) {
                System.out.println("Ocorreu um erro durante a tentativa de enviar uma mensagem ao Socket.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/consulta/{entidade}")
    public ResponseEntity<String> getClient(@PathVariable String entidade,
            @RequestBody(required = false) List<LinkedHashMap<String, String>> body) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("RESPONSE_SERVICE_URL", "http://host.docker.internal:8070/bff/print_users/request-bus/response");
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(body);
        } catch (Exception e) {
            System.out.println("Erro meu chapa");
        }

        HttpEntity<String> entity = new HttpEntity<>(json, headers);

        try {
            ResponseEntity<String> content = restTemplate.exchange(caminhoDoRequestBus + entidade, HttpMethod.POST,
                    entity, String.class);
            return ResponseEntity.ok(content.getBody());
        } catch (HttpClientErrorException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        }

    }

    @GetMapping("/menu")
    public ResponseEntity<MenuItems> menuItem(@RequestHeader(value = "Authorization") String token) {
        MenuItems menu = new MenuItems();

        menu.montaMenu(token);

        if (menu.getItems().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.ok(menu);
    }

}

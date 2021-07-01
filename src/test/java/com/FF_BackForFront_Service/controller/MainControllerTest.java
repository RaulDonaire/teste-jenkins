package com.FF_BackForFront_Service.controller;

import static org.mockito.Mockito.mock;

import java.net.URI;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.FF_BackForFront_Service.model.Mensagem;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.socket.client.Socket;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureStubRunner(ids = {
        "com.icarotech.flexforward:ff-request-bus:+:stubs:8090" }, stubsMode = StubRunnerProperties.StubsMode.REMOTE, repositoryRoot = "http://localhost:8082/repository/maven-snapshots/")
@TestPropertySource(properties = { "caminho.request.bus=http://localhost:8090/ff-request-bus/" })


public class MainControllerTest {

    private MockMvc mockMvc;
    private MainController controller;

    @BeforeEach
    public void setup() {
        controller = new MainController() {
            @Override
            protected Socket createSocket() {
                Socket socket = mock(Socket.class);
                return socket;
            }
        };
        controller.setCaminhoRequestBus("http://localhost:8090/ff-request-bus/");
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void deveriaDevolver200CasoDadosEnviadosEstejamCorretos() throws Exception {
        URI uri = new URI("/menu");

        // token de autenticação válido
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJvd0syVDNNY1JnNDREQl8yX1cycGdwb3VwYlJGWmFEbk9rODVKOGh2dUVNIn0.eyJleHAiOjE2MjQ1NTczODYsImlhdCI6MTYyNDU1NzA4NiwianRpIjoiMjc3MTJhNTQtNTBhZi00MTMyLTlhZjYtMzA3YzBmZmE2ZDZmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdC9hdXRoL3JlYWxtcy90ZXN0ZSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmN2VlNjc1My05ZDcwLTRhNWQtOGNmZC0zMzdiN2EyZGE3NzkiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjbGllbnQxIiwic2Vzc2lvbl9zdGF0ZSI6IjE0ZDFkMWIzLTMxODEtNDkzNS1iYTc4LWQwMmFiOTM4MDhhMiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsImRlZmF1bHQtcm9sZXMtcmVhbG0xIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJKb3NlcGggSm9lc3RhciIsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIxIiwiZ2l2ZW5fbmFtZSI6Ikpvc2VwaCIsImZhbWlseV9uYW1lIjoiSm9lc3RhciJ9.b4uOC8TMs-dYqLiecydirGvSbIYzVPnR1vvhNO71mgfT6AUOTY8bUp5bo_GSvnUVsV8RcW1r0sO4bL3f66xKVTvfMvfeIY0V_Lq2u0_lk1qwp12RDS3QZiGHY69CTpaPN1O630lCj7GTJmBDSDvg7nTAHIubHg9XW1_yKAcapMKV3pVGPPzNtdFLS1obwPI6dUagH7g_2PmEvIhggUbsqptfsTUsqzobozNYDC-doneg2vN9U5e-xwGFfEQTmDpamW-g3t_4o6RgqHc5Vsm3QE7HtTHBuWfnvxZO2ChAzOpfot6xls6YawqK6xBvMeCR5H1ufogjgePS3-9D2bMKPA";

        mockMvc.perform(
                MockMvcRequestBuilders.get(uri).header("Authorization", token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

    @Test
    public void deveriaDevolver400CasoDadosEnviadosSemToken() throws Exception {
        URI uri = new URI("/menu");

        mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaDevolver400CasoTokenIncompativel() throws Exception {
        URI uri = new URI("/menu");

        // token de autenticação válido
        String token = "claramenteNaoEhUmToken";

        mockMvc.perform(
                MockMvcRequestBuilders.get(uri).header("Authorization", token).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaEnviarUmaMensagemNormalmentePeloWebSocket() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        URI uri = new URI("/print_users/request-bus/response");
        Date dataTeste = new Date();
        UUID uuid = UUID.randomUUID();
        List<Object> raw = new ArrayList<>();
        Mensagem mensagemEnviada = new Mensagem("nome mensagem", "texto mensagem", "001", uuid, "coletor", 1, dataTeste,
                dataTeste, raw, "destinatario");
        String jsonEnviado = mapper.writeValueAsString(mensagemEnviada);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonEnviado))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    public void deveriaDarErro400CasoWebsocketEstejaOffline() throws Exception {
        controller = new MainController() {
            @Override
            protected Socket createSocket() {
                // websocket não existente
                return null;
            }
        };
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        ObjectMapper mapper = new ObjectMapper();
        URI uri = new URI("/print_users/request-bus/response");
        Date dataTeste = new Date();
        UUID uuid = UUID.randomUUID();
        List<Object> raw = new ArrayList<>();
        Mensagem mensagemEnviada = new Mensagem("nome mensagem", "texto mensagem", "001", uuid, "coletor", 1, dataTeste,
                dataTeste, raw, "destinatario");
        String jsonEnviado = mapper.writeValueAsString(mensagemEnviada);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonEnviado))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaDarErro400CasoMensagemInválida() throws Exception {
        URI uri = new URI("/print_users/request-bus/response");

        String jsonEnviado = "{\"variavel\": \"obviamenteNaoSouUmaMensagem\"}";

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonEnviado))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaDarErro400CasoMensagemSemUUID() throws Exception {
        URI uri = new URI("/print_users/request-bus/response");
        ObjectMapper mapper = new ObjectMapper();
        Date dataTeste = new Date();
        List<Object> raw = new ArrayList<>();
        Mensagem mensagemEnviada = new Mensagem("nome mensagem", "texto mensagem", "001", null, "coletor", 1, dataTeste,
                dataTeste, raw, "destinatario");

        String jsonEnviado = mapper.writeValueAsString(mensagemEnviada);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonEnviado))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaDarErro400CasoMensagemComNameNULL() throws Exception {
        URI uri = new URI("/print_users/request-bus/response");
        ObjectMapper mapper = new ObjectMapper();
        Date dataTeste = new Date();
        List<Object> raw = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Mensagem mensagemEnviada = new Mensagem(null, "texto mensagem", "001", uuid, "coletor", 1, dataTeste, dataTeste,
                raw, "destinatario");

        String jsonEnviado = mapper.writeValueAsString(mensagemEnviada);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonEnviado))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaDarErro400CasoMensagemComNameVazio() throws Exception {
        URI uri = new URI("/print_users/request-bus/response");
        ObjectMapper mapper = new ObjectMapper();
        Date dataTeste = new Date();
        List<Object> raw = new ArrayList<>();
        UUID uuid = UUID.randomUUID();
        Mensagem mensagemEnviada = new Mensagem("", "texto mensagem", "001", uuid, "coletor", 1, dataTeste, dataTeste,
                raw, "destinatario");

        String jsonEnviado = mapper.writeValueAsString(mensagemEnviada);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(jsonEnviado))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaDarErro400CasoSemMensagem() throws Exception {
        URI uri = new URI("/print_users/request-bus/response");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    public void deveriaRetornar404ParaQueryDesconhecida() throws Exception {
        URI uri = new URI("/consulta/get_unknown_query");
        ObjectMapper mapper = new ObjectMapper();
        List<LinkedHashMap<String, String>> body = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> teste = new LinkedHashMap<String, String>();
        teste.put("testando1", "testando2");
        body.add(teste);
        String json = mapper.writeValueAsString(body);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(404));

    }

    @Test
    public void deveriaRetornar400ParaQuerySemHeader() throws Exception {
        URI uri = new URI("/consulta/get_contract_test");
        ObjectMapper mapper = new ObjectMapper();
        List<LinkedHashMap<String, String>> body = new ArrayList<LinkedHashMap<String, String>>();
        LinkedHashMap<String, String> teste = new LinkedHashMap<String, String>();
        teste.put("testando1", "testando2");
        body.add(teste);
        String json = mapper.writeValueAsString(body);

        mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.status().is(200));

    }

}

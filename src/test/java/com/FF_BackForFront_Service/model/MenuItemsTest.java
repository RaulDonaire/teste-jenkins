package com.FF_BackForFront_Service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;




public class MenuItemsTest {

    MenuItems menu;

    @Test
    public void menuDeveriaConterGetUsersCasoUsuarioSejaAdmin(){

        this.menu = new MenuItems();

        //token de um usuário com a role admin
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJvd0syVDNNY1JnNDREQl8yX1cycGdwb3VwYlJGWmFEbk9rODVKOGh2dUVNIn0.eyJleHAiOjE2MjQ1NTczODYsImlhdCI6MTYyNDU1NzA4NiwianRpIjoiMjc3MTJhNTQtNTBhZi00MTMyLTlhZjYtMzA3YzBmZmE2ZDZmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdC9hdXRoL3JlYWxtcy90ZXN0ZSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiJmN2VlNjc1My05ZDcwLTRhNWQtOGNmZC0zMzdiN2EyZGE3NzkiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjbGllbnQxIiwic2Vzc2lvbl9zdGF0ZSI6IjE0ZDFkMWIzLTMxODEtNDkzNS1iYTc4LWQwMmFiOTM4MDhhMiIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJhZG1pbiIsImRlZmF1bHQtcm9sZXMtcmVhbG0xIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJKb3NlcGggSm9lc3RhciIsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIxIiwiZ2l2ZW5fbmFtZSI6Ikpvc2VwaCIsImZhbWlseV9uYW1lIjoiSm9lc3RhciJ9.b4uOC8TMs-dYqLiecydirGvSbIYzVPnR1vvhNO71mgfT6AUOTY8bUp5bo_GSvnUVsV8RcW1r0sO4bL3f66xKVTvfMvfeIY0V_Lq2u0_lk1qwp12RDS3QZiGHY69CTpaPN1O630lCj7GTJmBDSDvg7nTAHIubHg9XW1_yKAcapMKV3pVGPPzNtdFLS1obwPI6dUagH7g_2PmEvIhggUbsqptfsTUsqzobozNYDC-doneg2vN9U5e-xwGFfEQTmDpamW-g3t_4o6RgqHc5Vsm3QE7HtTHBuWfnvxZO2ChAzOpfot6xls6YawqK6xBvMeCR5H1ufogjgePS3-9D2bMKPA";

        this.menu.montaMenu(token);

        assertEquals(true, this.menu.contemServico("Get Users"));

    }


    @Test
    public void menuNaoDeveriaConterGetUsersCasoUsuarioNaoSejaAdmin(){

        this.menu = new MenuItems();

        //token de um usuário sem a role admin
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJvd0syVDNNY1JnNDREQl8yX1cycGdwb3VwYlJGWmFEbk9rODVKOGh2dUVNIn0.eyJleHAiOjE2MjQ1NjUzNDEsImlhdCI6MTYyNDU2NTA0MSwianRpIjoiYTk5Y2RmYjctYmZjMy00ZjA0LTg4MWUtOWQ2YjMzZTNmOGMxIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdC9hdXRoL3JlYWxtcy90ZXN0ZSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI0NjAwOTJkYi0yZDhkLTRlNTAtYjFlZC03ZDFkZGY2ODMxNzgiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjbGllbnQxIiwic2Vzc2lvbl9zdGF0ZSI6IjBmOTdlMjQ3LTI0MzQtNDc0My1hMGZkLTRiZDFmZjY5NzljYyIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJkZWZhdWx0LXJvbGVzLXJlYWxtMSIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJwcm9maWxlIGVtYWlsIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiUmljayBBc3RsZXkiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJ1c2VyMiIsImdpdmVuX25hbWUiOiJSaWNrIiwiZmFtaWx5X25hbWUiOiJBc3RsZXkifQ.StexfL7Qz8BLUGyqca_GKuxq_4QIhl7vE1Xz9GvZVpwcoyeRJDTolmcFPDPAf1T_xGizOR8J8Dna80ARt5IH2Ug44pGzg0H8VGnNQCF-JpJyhO3T6T4zzzgooZ8jxB-riGCKcT2xNd34Mj4rioUOSu848Y4BKGgYFBlu2TzUcdpils8ODXJwPC0U47s_OP_MBjyNAhH-O0-Kz6PoXCKgwOyeW1CP2d26e5ilXCATweA9bnxdAsIagL7pB7fg_sZCGqUsuIUStqIodPU_bPWfTtS1gpBAPTDGEmzeLBMbu9sQyh2xXAK6LQvAoi5bxeTLBiNKeEJXTBfWrH77hlwnSg";

        this.menu.montaMenu(token);

        assertEquals(false, this.menu.contemServico("Get Users"));

    }

    @Test
    public void menuDeveriaEstarVazioCasoTokenInvalido(){

        this.menu = new MenuItems();

        String token = "claramenteNaoEhumToken";

        this.menu.montaMenu(token);

        assertEquals(true, this.menu.getItems().isEmpty());

    }
}

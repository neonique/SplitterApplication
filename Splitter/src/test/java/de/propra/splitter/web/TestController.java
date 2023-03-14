package de.propra.splitter.web;


import de.propra.splitter.service.ApplicationService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SplitterController.class)
public class TestController {

  @MockBean
  ApplicationService service;

  @Autowired
  MockMvc mockMvc;


  @Test
  @DisplayName("nach login wird man zu alle gruppen weitergeleitet")
  void test_1() throws Exception {
    mockMvc.perform(get("/").with(oauth2Login()))
        .andExpect(redirectedUrl("/alleGruppen"));
  }

  @Test
  @DisplayName("get auf /alleGruppen holt sich die nutzergruppen")
  void test_2() throws Exception {
    mockMvc.perform(get("/alleGruppen").with(oauth2Login()));
    verify(service).offeneNutzerGruppen(any());
    verify(service).geschlosseneNutzerGruppen(any());
  }

  @Test
  @DisplayName("get auf /alleGruppen gibt entsprechende html zurück")
  void test_3() throws Exception {
    mockMvc.perform(get("/alleGruppen").with(oauth2Login()))
        .andExpect(status().isOk())
        .andExpect(view().name("alleGruppen"));

  }

  @Test
  @DisplayName("post auf /alleGruppen leitet auf /alleGruppen zurück")
  void test_4() throws Exception {
    mockMvc.perform(post("/alleGruppen")
        .param("neueGruppe","").with(oauth2Login()).with(csrf()))
        .andExpect(redirectedUrl("/alleGruppen"));

  }

  @Test
  @DisplayName("get auf /gruppe holt sich Transaktionen")
  void test_5() throws Exception {
    mockMvc.perform(get("/gruppe").with(oauth2Login())
            .flashAttr("gruppenid", "boo"));
    verify(service).getGruppenTransaktionen(any());
  }

  @Test
  @DisplayName("get auf /gruppe gibt entsprechende html zurück")
  void test_6() throws Exception {
    mockMvc.perform(get("/gruppe")
        .flashAttr("gruppenid", "boo").with(oauth2Login()))
        .andExpect(view().name("gruppe"));

  }

  @Test
  @DisplayName("get auf /gruppe mit leerer gruppenid redirected auf /alleGruppen")
  void test_7() throws Exception {
    mockMvc.perform(get("/gruppe")
            .flashAttr("gruppenid", "").with(oauth2Login()))
        .andExpect(redirectedUrl("/alleGruppen"));

  }

  @Test
  @DisplayName("get auf /gruppe/auswahl redirected auf gruppe")
  void test_8() throws Exception {
    mockMvc.perform(get("/gruppe/auswahl")
            .param("gruppenid", "").with(oauth2Login()))
        .andExpect(redirectedUrl("/gruppe"));

  }

  @Test
  @DisplayName("post auf /gruppe/close schließt gruppe")
  void test_9() throws Exception {
    mockMvc.perform(post("/gruppe/close")
            .param("gruppenid","boo").with(oauth2Login()).with(csrf()));
        verify(service).closeGruppe("boo");

  }

  @Test
  @DisplayName("post auf /gruppe/close redirected auf gruppe")
  void test_10() throws Exception {
    mockMvc.perform(post("/gruppe/close")
        .param("gruppenid","boo").with(oauth2Login()).with(csrf()))
        .andExpect(redirectedUrl("/gruppe"));

  }

  @Test
  @DisplayName("post auf /gruppe/close redirected auf /alleGruppen bei leerer gruppenid")
  void test_11() throws Exception {
    mockMvc.perform(post("/gruppe/close")
            .param("gruppenid","").with(oauth2Login()).with(csrf()))
        .andExpect(redirectedUrl("/alleGruppen"));

  }

  @Test
  @DisplayName("post auf /gruppe/neuerNutzer fügt nutzer hinzu")
  void test_12() throws Exception {
    mockMvc.perform(post("/gruppe/neuerNutzer")
        .param("gruppenid","id")
        .param("neuerNutzer", "meow").with(oauth2Login()).with(csrf()));
    verify(service).addNutzerToGruppe("id", "meow");
  }

  @Test
  @DisplayName("post auf /gruppe/neuerNutzer redirected auf /gruppe")
  void test_13() throws Exception {
    mockMvc.perform(post("/gruppe/neuerNutzer")
        .param("gruppenid","id")
        .param("neuerNutzer", "meow").with(oauth2Login()).with(csrf()))
        .andExpect(redirectedUrl("/gruppe"));

  }

  @Test
  @DisplayName("get auf /gruppe/addTransaktion redirected auf /gruppe/neueTransaktion")
  void test_14() throws Exception {
    mockMvc.perform(get("/gruppe/addTransaktion")
            .param("gruppenid","id")
            .with(oauth2Login()))
        .andExpect(redirectedUrl("/neueTransaktion"));

  }

  @Test
  @DisplayName("post auf /gruppe/addTransaktion redirected auf /alleGruppen bei leerer gruppenid")
  void test_15() throws Exception {
    mockMvc.perform(get("/gruppe/addTransaktion")
            .param("gruppenid","").with(oauth2Login()))
        .andExpect(redirectedUrl("/alleGruppen"));

  }

  @Test
  @DisplayName("get auf /neueTransaktion returned die entsprechende html")
  void test_16() throws Exception {
    mockMvc.perform(get("/neueTransaktion")
            .flashAttr("gruppenid","id")
            .with(oauth2Login()))
        .andExpect(view().name("addTransaktion"));

  }

}


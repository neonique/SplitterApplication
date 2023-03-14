package de.propra.splitter.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.propra.splitter.api.records.GruppeBasicDataAPI;
import de.propra.splitter.config.SecurityConfig;
import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.service.ApplicationService;
import io.swagger.v3.oas.models.links.Link;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

//Alle Tests der vorgebenen run_test.jar laufen
@ImportAutoConfiguration(classes = SecurityConfig.class)
@WebMvcTest(ApiController.class)
@Import(ApiController.class)
@AutoConfigureMockMvc
public class TestApiController {

  @Autowired
  private MockMvc mvc;

  @MockBean
  ApplicationService applicationService;

  @Test
  @DisplayName("api/gruppen ohne RequestBody")
  void test_1() throws Exception{
    mvc.perform(post("/api/gruppen"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit Konformem JSON")
  void test_2() throws Exception{
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "TestGruppe",
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
        .content(jsonGruppe))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("api/gruppen mit id")
  void test_3() throws Exception{
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI("fakeid", "TestGruppe",
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    System.out.println(jsonGruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit leeren Personen")
  void test_4() throws Exception{
    LinkedList<String> personen = new LinkedList<>();
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "TestGruppe",
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    System.out.println(jsonGruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit leerem Gruppenname")
  void test_5() throws Exception{
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "",
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    System.out.println(jsonGruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit Gruppenname == null")
  void test_6() throws Exception{
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, null,
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    System.out.println(jsonGruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit personen == null")
  void test_7() throws Exception{
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "gruppe",
        null);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    System.out.println(jsonGruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }
  @Test
  @DisplayName("api/user/{nutzername}/gruppen mit unbekanntem Nutzer")
  void test_8() throws Exception{

    String nutzername = "Jeremy";

    MvcResult mvcResult = mvc.perform(get("/api/user/{nutzername}/gruppen", nutzername))
        .andExpect(status().isOk()).andReturn();

    assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("[]");
  }

}

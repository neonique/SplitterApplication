package de.propra.splitter.api;

import static org.mockito.AdditionalMatchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.propra.splitter.api.records.GruppeBasicDataAPI;
import de.propra.splitter.config.SecurityConfig;
import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.service.ApplicationService;
import io.swagger.v3.oas.models.links.Link;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ImportAutoConfiguration(classes = SecurityConfig.class)
@WebMvcTest(ApiController.class)
@AutoConfigureMockMvc
public class TestApiController {

  @Autowired
  private MockMvc mvc;

  @MockBean
  ApplicationService service;

  @Test
  @DisplayName("api/gruppen ohne Parameter 400")
  void test_1() throws Exception{
    mvc.perform(post("/api/gruppen"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit JSON")
  void test_2() throws Exception{
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "TestGruppe",
        personen);
  }
}

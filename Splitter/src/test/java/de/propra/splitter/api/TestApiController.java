package de.propra.splitter.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.propra.splitter.api.records.AusgabenDataAPI;
import de.propra.splitter.api.records.AusgleichDataAPI;
import de.propra.splitter.api.records.GruppeBasicDataAPI;
import de.propra.splitter.api.records.GruppenDataDetailedAPI;
import de.propra.splitter.config.SecurityConfig;
import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.persistence.data.GruppeData;
import de.propra.splitter.service.ApplicationService;
import io.swagger.v3.oas.models.links.Link;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.constraints.Max;
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
  void test_1() throws Exception {
    mvc.perform(post("/api/gruppen"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit Konformem JSON")
  void test_2() throws Exception {
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
  void test_3() throws Exception {
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI("fakeid", "TestGruppe",
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit leeren Personen")
  void test_4() throws Exception {
    LinkedList<String> personen = new LinkedList<>();
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "TestGruppe",
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit leerem Gruppenname")
  void test_5() throws Exception {
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "",
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit Gruppenname == null")
  void test_6() throws Exception {
    LinkedList<String> personen = new LinkedList<>(List.of("Peter", "Jeremy"));
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, null,
        personen);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen mit personen == null")
  void test_7() throws Exception {
    GruppeBasicDataAPI gruppe = new GruppeBasicDataAPI(null, "gruppe",
        null);

    String jsonGruppe = new ObjectMapper().writeValueAsString(gruppe);
    String id = UUID.randomUUID().toString();
    when(applicationService.addGruppe(any(), any())).thenReturn(id);

    mvc.perform(post("/api/gruppen")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonGruppe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/user/{nutzername}/gruppen mit unbekanntem Nutzer")
  void test_8() throws Exception {

    String nutzername = "Jeremy";

    MvcResult mvcResult = mvc.perform(get("/api/user/{nutzername}/gruppen", nutzername))
        .andExpect(status().isOk()).andReturn();

    assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("[]");
  }

  @Test
  @DisplayName("api/user/{nutzername}/gruppen mit bekanntem Nutzer")
  void test_9() throws Exception {

    //Arrange
    String nutzername = "Jeremy";
    HashMap<String, String> nutzerGruppen = new HashMap<>();
    nutzerGruppen.put("111", "gruppe1");
    nutzerGruppen.put("112", "Feuerwehr");
    when(applicationService.nutzerGruppen(nutzername)).thenReturn(nutzerGruppen);

    Set<String> teilnehmer1 = new HashSet<>(List.of("Jeremy", "fabian", "Damian"));
    Set<String> teilnehmer2 = new HashSet<>(List.of("Fragrance", "fabian", "Jeremy"));
    when(applicationService.getGruppenNutzer("111")).thenReturn(teilnehmer1);
    when(applicationService.getGruppenNutzer("112")).thenReturn(teilnehmer2);

    LinkedList<GruppeBasicDataAPI> gruppenDataList = new LinkedList<>();
    GruppeBasicDataAPI gruppeBasicData1 = new GruppeBasicDataAPI("111", "gruppe1",
        new LinkedList<>(teilnehmer1));
    GruppeBasicDataAPI gruppeBasicData2 = new GruppeBasicDataAPI("112", "Feuerwehr",
        new LinkedList<>(teilnehmer2));
    gruppenDataList.addAll(List.of(gruppeBasicData1, gruppeBasicData2));

    String jsonGruppenDataList = new ObjectMapper().writeValueAsString(gruppenDataList);

    //Act
    //Assert
    mvc.perform(get("/api/user/{nutzername}/gruppen", nutzername))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonGruppenDataList));

  }

  @Test
  @DisplayName("api/gruppen/{id} mit unbekannter id")
  void test_10() throws Exception {
    String id = "wdaofjiog";

    when(applicationService.exists(id)).thenReturn(false);

    //Act
    //Assert
    mvc.perform(get("/api/gruppen/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("api/gruppen/{id} mit bekannter ID")
  void test_11() throws Exception {

    //Arrange
    String id = "123";
    when(applicationService.exists(id)).thenReturn(true);

    List<TransaktionDTO> transaktionen = new ArrayList<>();
    TransaktionDTO t1 = new TransaktionDTO("Jason", Set.of("Jason", "Jeremy"), 88.65,
        "Trinken");
    TransaktionDTO t2 = new TransaktionDTO("Pascal", Set.of("Jason", "Jeremy"), 87.65,
        "Essen");
    transaktionen.addAll(List.of(t1, t2));
    when(applicationService.getGruppenTransaktionen(id)).thenReturn(transaktionen);

    String gruppenname = "code jam 22";
    Set<String> nutzer = Set.of("Jason", "Jeremy", "Pascal");
    boolean closed = false;
    when(applicationService.getName(id)).thenReturn(gruppenname);
    when(applicationService.getGruppenNutzer(id)).thenReturn(nutzer);
    when(applicationService.isClosed(id)).thenReturn(closed);

    LinkedList<AusgabenDataAPI> ausgaben = new LinkedList<>();
    ausgaben.add(new AusgabenDataAPI(t1.grund(), t1.sponsor(), (int) (t1.betrag() * 100),
        new LinkedList<>(t1.bettler())));
    ausgaben.add(new AusgabenDataAPI(t2.grund(), t2.sponsor(), (int) (t2.betrag() * 100),
        new LinkedList<>(t2.bettler())));

    GruppenDataDetailedAPI gruppeData = new GruppenDataDetailedAPI(id, gruppenname,
        new LinkedList<>(nutzer), closed, ausgaben);
    String jsonGruppeDataDetailed = new ObjectMapper().writeValueAsString(gruppeData);
    //Act
    //Assert
    mvc.perform(get("/api/gruppen/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonGruppeDataDetailed));
  }

  @Test
  @DisplayName("api/gruppen/{id}/schliessen mit unbekannter id")
  void test_12() throws Exception {
    String id = "duubeedoobaa";

    when(applicationService.exists(id)).thenReturn(false);

    mvc.perform(post("/api/gruppen/{id}/schliessen", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("api/gruppen/{id}/schliessen mit bekannter id")
  void test_13() throws Exception {
    String id = "duubeedoobaa";

    when(applicationService.exists(id)).thenReturn(true);

    mvc.perform(post("/api/gruppen/{id}/schliessen", id))
        .andExpect(status().isOk());
    verify(applicationService).closeGruppe(id);
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit ungültigem response body: grund null")
  void test_14() throws Exception {
    String id = "duubeedoobaa";
    AusgabenDataAPI ausgabe = new AusgabenDataAPI(null, "Fabi", 800,
        new LinkedList<>(List.of("Bastel")));

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit ungültigem response body: gläubiger null")
  void test_15() throws Exception {
    String id = "duubeedoobaa";
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", null, 800,
        new LinkedList<>(List.of("Bastel")));

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit ungültigem response body: betrag 0")
  void test_16() throws Exception {
    String id = "duubeedoobaa";
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", "Fabi", 0,
        new LinkedList<>(List.of("Bastel")));

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isBadRequest());
  }


  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit ungültigem response body: Bettler null")
  void test_17() throws Exception {
    String id = "duubeedoobaa";
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", "Fabi", 6666,
        null);

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit ungültigem response body: keine Bettler")
  void test_18() throws Exception {
    String id = "duubeedoobaa";
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", "Fabi", 6666,
        new LinkedList<>());

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit gültigem response body")
  void test_19() throws Exception {
    String id = "duubeedoobaa";
    Set<String> bettler = Set.of("Peter", "Shawn");
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", "Fabi", 6666,
        new LinkedList<>(bettler));

    when(applicationService.exists(id)).thenReturn(true);
    when(applicationService.isClosed(id)).thenReturn(false);

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isCreated());

    verify(applicationService).addTransaktionToGruppe(id, "Fabi", bettler, 66.66,
        "Trinken");
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit ungültigem response body: Ungültiger Nutzer")
  void test_20() throws Exception {
    String id = "duubeedoobaa";
    Set<String> bettler = Set.of("Peter", "Shawn");
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", "Fabi", 6666,
        new LinkedList<>(bettler));

    when(applicationService.exists(id)).thenReturn(true);
    when(applicationService.isClosed(id)).thenReturn(false);
    doThrow(new IllegalArgumentException()).when(applicationService)
        .addTransaktionToGruppe(id, "Fabi", bettler, 66.66,
            "Trinken");

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit ungültiger ID")
  void test_21() throws Exception {
    String id = "duubeedoobaa";
    Set<String> bettler = Set.of("Peter", "Shawn");
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", "Fabi", 6666,
        new LinkedList<>(bettler));

    when(applicationService.exists(id)).thenReturn(false);

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("api/gruppen/{id}/auslagen mit geschlossener gruppe")
  void test_22() throws Exception {
    String id = "duubeedoobaa";
    Set<String> bettler = Set.of("Peter", "Shawn");
    AusgabenDataAPI ausgabe = new AusgabenDataAPI("Trinken", "Fabi", 6666,
        new LinkedList<>(bettler));

    when(applicationService.exists(id)).thenReturn(true);
    when(applicationService.isClosed(id)).thenReturn(true);

    String jsonAusgabe = new ObjectMapper().writeValueAsString(ausgabe);

    mvc.perform(post("/api/gruppen/{id}/auslagen", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonAusgabe))
        .andExpect(status().isConflict());
  }

  @Test
  @DisplayName("api/gruppen/{id}/ausgleich mit unbekannter gruppe")
  void test_23() throws Exception {
    String id = "duubeedoobaa";

    when(applicationService.exists(id)).thenReturn(false);

    mvc.perform(get("/api/gruppen/{id}/ausgleich", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("api/gruppen/{id}/ausgleich mit bekannter gruppe")
  void test_24() throws Exception {
    String id = "duubeedoobaa";

    AusgleichDataAPI a1 = new AusgleichDataAPI("Jeremy", "Justus", 3000);
    AusgleichDataAPI a2 = new AusgleichDataAPI("Jeremy", "Fabian", 2000);
    AusgleichDataAPI a3 = new AusgleichDataAPI("Luis", "Fabian", 1500);

    when(applicationService.exists(id)).thenReturn(true);

    HashMap<String, HashMap<String, Double>> transaktionen = new HashMap<>();

    HashMap<String, Double> a1Transaktionen = new HashMap<>();
    a1Transaktionen.put(a1.an(), ((double) (a1.cents()) / 100));
    a1Transaktionen.put(a2.an(), ((double) (a2.cents()) / 100));
    transaktionen.put(a1.von(), a1Transaktionen);

    HashMap<String, Double> a3Transaktionen = new HashMap<>();
    a3Transaktionen.put(a3.an(), ((double) (a3.cents()) / 100));
    transaktionen.put(a3.von(), a3Transaktionen);

    when(applicationService.notwendigeTransaktionen(id)).thenReturn(transaktionen);

    LinkedList<AusgleichDataAPI> ausgleichData = new LinkedList<>();
    ausgleichData.addAll(List.of(a1, a2, a3));
    String jsonAusgleich = new ObjectMapper().writeValueAsString(ausgleichData);

    mvc.perform(get("/api/gruppen/{id}/ausgleich", id))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonAusgleich));
  }

}

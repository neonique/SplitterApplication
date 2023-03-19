package de.propra.splitter.service;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;

import java.util.*;
import java.util.Map.Entry;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestApplicationService {

  @Test
  @DisplayName("Gruppe erstellen und speichern")
  void test_01() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);

    String id = applicationService.addGruppe("p39", "moaz");

    verify(gruppenRepo).save(any(Gruppe.class));
    assertThat(id).isNotNull();
  }

  @Test
  @DisplayName("Nutzergruppen IDs und Namen erhalten")
  void test_02() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String nutzername = "moaz";
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    String name1 = "g1";
    String name2 = "g2";
    Gruppe g1 = new Gruppe(name1, nutzername, id1);
    Gruppe g2 = new Gruppe(name2, nutzername, id2);
    Set<Gruppe> gruppen = Set.of(g1, g2);
    when(gruppenRepo.nutzerGruppen(nutzername)).thenReturn(gruppen);

    HashMap<String, String> nutzerGruppen = applicationService.nutzerGruppen(nutzername);

    Entry<String, String> e1 = Map.entry(id1.toString(), name1);
    Entry<String, String> e2 = Map.entry(id2.toString(), name2);
    assertThat(nutzerGruppen).containsOnly(e1, e2);
  }

  @Test
  @DisplayName("Offene Nutzergruppen IDs und Namen erhalten")
  void test_03() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String nutzername = "moaz";
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    String name1 = "g1";
    String name2 = "g2";
    Gruppe g1 = new Gruppe(name1, nutzername, id1);
    Gruppe g2 = new Gruppe(name2, nutzername, id2);
    g2.close();
    Set<Gruppe> gruppen = Set.of(g1, g2);
    when(gruppenRepo.nutzerGruppen(nutzername)).thenReturn(gruppen);

    HashMap<String, String> nutzerGruppen = applicationService.offeneNutzerGruppen(nutzername);

    Entry<String, String> e1 = Map.entry(id1.toString(), name1);
    assertThat(nutzerGruppen).containsOnly(e1);
  }


  @Test
  @DisplayName("Geschlossene Nutzergruppen IDs und Namen erhalten")
  void test_04() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String nutzername = "moaz";
    UUID id1 = UUID.randomUUID();
    UUID id2 = UUID.randomUUID();
    String name1 = "g1";
    String name2 = "g2";
    Gruppe g1 = new Gruppe(name1, nutzername, id1);
    Gruppe g2 = new Gruppe(name2, nutzername, id2);
    g2.close();
    Set<Gruppe> gruppen = Set.of(g1, g2);
    when(gruppenRepo.nutzerGruppen(nutzername)).thenReturn(gruppen);

    HashMap<String, String> nutzerGruppen = applicationService.geschlosseneNutzerGruppen(
        nutzername);

    Entry<String, String> e2 = Map.entry(id2.toString(), name2);
    assertThat(nutzerGruppen).containsOnly(e2);
  }

  @Test
  @DisplayName("Gruppe schließen und speichern")
  void test_05() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String name1 = "g1";
    String nutzername = "moaz";
    UUID id1 = UUID.randomUUID();
    Gruppe g1 = new Gruppe(name1, nutzername, id1);
    when(gruppenRepo.load(id1.toString())).thenReturn(g1);

    applicationService.closeGruppe(id1.toString());

    assertThat(g1.isclosed());
    verify(gruppenRepo).save(g1);
  }

  @Test
  @DisplayName("Nutzer zu Gruppe hinzufuegen und speichern")
  void test_06() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String name1 = "g1";
    String nutzername = "moaz";
    String neuerNutzer = "marco"; //hihi
    UUID id1 = UUID.randomUUID();
    Gruppe g1 = new Gruppe(name1, nutzername, id1);
    when(gruppenRepo.load(id1.toString())).thenReturn(g1);

    applicationService.addNutzerToGruppe(id1.toString(), neuerNutzer);

    assertThat(g1.containsNutzer(neuerNutzer));
    verify(gruppenRepo).save(g1);
  }

  @Test
  @DisplayName("Transaktionen zu Gruppe hinzufuegen und speichern")
  void test_07() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String name1 = "g1";
    String sponsor = "moaz";
    String bettler = "marco"; //hihi haha
    double betrag = 100 - 31; //haha funny number
    UUID id1 = UUID.randomUUID();
    Gruppe g1 = new Gruppe(name1, sponsor, id1);
    TransaktionDTO transaktionDTO = new TransaktionDTO(sponsor, Set.of(bettler), betrag, "");
    g1.addNutzer(bettler);
    when(gruppenRepo.load(id1.toString())).thenReturn(g1);

    applicationService.addTransaktionToGruppe(id1.toString(), sponsor, Set.of(bettler), betrag, "");

    assertThat(g1.getTransaktionenDetails()).containsOnly(transaktionDTO);
    verify(gruppenRepo).save(g1);
  }

  @Test
  @DisplayName("Nutzer einer Gruppe ausgeben")
  void test_08() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String name1 = "g1";
    String nutzername1 = "moaz";
    String nutzername2 = "marco";
    UUID id1 = UUID.randomUUID();
    when(gruppenRepo.gruppeNutzer(id1.toString())).thenReturn(Set.of(nutzername1, nutzername2));

    Set<String> nutzernamen = applicationService.getGruppenNutzer(id1.toString());

    assertThat(nutzernamen).containsOnly(nutzername1, nutzername2);
  }

  @Test
  @DisplayName("Transaktionen einer Gruppe ausgeben")
  void test_09() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    String name1 = "g1";
    String nutzername1 = "moaz";
    String nutzername2 = "marco";
    double betrag1 = 22;
    double betrag2 = 23;
    TransaktionDTO t1 = new TransaktionDTO(nutzername1, Set.of(nutzername2), betrag1, "");
    TransaktionDTO t2 = new TransaktionDTO(nutzername2, Set.of(nutzername1, nutzername2), betrag2,
        "");
    List<TransaktionDTO> transaktionDTOS = List.of(t1, t2);

    UUID id1 = UUID.randomUUID();
    when(gruppenRepo.gruppeTransaktionen(id1.toString())).thenReturn(transaktionDTOS);

    List<TransaktionDTO> transaktionen = applicationService.getGruppenTransaktionen(id1.toString());

    assertThat(transaktionen).containsAll(transaktionDTOS);
  }


  @Test
  @DisplayName("Notwendige transaktionen ausgeben")
  void test_10() {
    GruppenRepo gruppenRepo = mock(GruppenRepo.class);
    ApplicationService applicationService = new ApplicationService(gruppenRepo);
    Gruppe gruppe = mock(Gruppe.class);
    String id = UUID.randomUUID().toString();
    when(gruppenRepo.load(id)).thenReturn(gruppe);

    HashMap<String, HashMap<String, Double>> notwendigeTransaktionen =
        applicationService.notwendigeTransaktionen(id);

    verify(gruppe).notwendigeTransaktionen();
  }

}

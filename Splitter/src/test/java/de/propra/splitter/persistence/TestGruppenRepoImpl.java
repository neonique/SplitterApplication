package de.propra.splitter.persistence;

import static org.mockito.Mockito.mock;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.persistence.dataRepos.GruppeDataRepo;
import de.propra.splitter.persistence.dataRepos.GruppeNutzerDataRepo;
import de.propra.splitter.persistence.dataRepos.TransaktionDataRepo;
import de.propra.splitter.persistence.dataRepos.TransaktionNutzerDataRepo;
import de.propra.splitter.service.ApplicationService;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.jdbc.Sql;

@DataJdbcTest
@ActiveProfiles("test")
@Sql("/createTable.sql")
@ComponentScan({"de.propra.splitter.service", "de.propra.splitter.persistence"})
public class TestGruppenRepoImpl {


  @Autowired
  ApplicationService service;
  @Autowired
  GruppeDataRepo gruppeDataRepo;
  @Autowired
  GruppeNutzerDataRepo gruppeNutzerDataRepo;
  @Autowired
  TransaktionNutzerDataRepo transaktionNutzerDataRepo;
  @Autowired
  TransaktionDataRepo transaktionDataRepo;


  GruppenRepoImpl gruppenRepo;

  @BeforeEach
  void init(){
    gruppenRepo = new GruppenRepoImpl(gruppeDataRepo, gruppeNutzerDataRepo,
        transaktionNutzerDataRepo, transaktionDataRepo);
  }

  @Test
  @DisplayName("Gruppe speichern funktioniert")
  public void test_01() {
    Gruppe gruppe = new Gruppe("überfordert", "jeder");
    gruppenRepo.save(gruppe);
    String id = gruppe.id();

    assertThat(gruppenRepo.exists(id)).isTrue();

  }

  @Test
  @DisplayName("Gruppe laden funktioniert")
  public void test_02() {
    Gruppe gruppe = new Gruppe("überfordert", "jeder");
    gruppenRepo.save(gruppe);
    String id = gruppe.id();
    Gruppe gespeicherteGruppe = gruppenRepo.load(id);
    assertThat(gruppe).isEqualTo(gespeicherteGruppe);

  }

  @Test
  @DisplayName("nutzerGruppen übergibt alle Gruppen wo Nutzer enthalten ist")
  public void test_03() {
    Gruppe gruppe1 = new Gruppe("überfordert", "jeder");
    Gruppe gruppe2 = new Gruppe("urlauuuuub", "jeder");
    Gruppe gruppe3 = new Gruppe("cries", "me");
    gruppenRepo.save(gruppe1);
    gruppenRepo.save(gruppe2);
    gruppenRepo.save(gruppe3);

    Set<Gruppe> nutzerGruppen = gruppenRepo.nutzerGruppen("jeder");

    assertThat(nutzerGruppen).contains(gruppe1, gruppe2);

  }

  @Test
  @DisplayName("gruppeNutzer gibt alle teilnehmer der Gruppe zurück")
  public void test_04() {
    Gruppe gruppe1 = new Gruppe("überfordert", "jeder");
    gruppenRepo.save(gruppe1);
    String id = gruppe1.id();
    service.addNutzerToGruppe(id, "meh");
    service.addNutzerToGruppe(id, "meow");

    Set<String> teilnehmer = gruppenRepo.gruppeNutzer(id);

    assertThat(teilnehmer).contains("meh", "meow", "jeder");

  }

  @Test
  @DisplayName("gruppeTransaktionen gibt alle transaktionen der Gruppe zurück")
  public void test_05() {
    Gruppe gruppe1 = new Gruppe("überfordert", "jeder");
    gruppenRepo.save(gruppe1);
    String id = gruppe1.id();
    service.addNutzerToGruppe(id, "meh");
    service.addNutzerToGruppe(id, "meow");
    Set<String> bettler = Set.of("meow", "jeder");

    service.addTransaktionToGruppe(id, "meh", bettler, 2.3, "am sterben");
    List<TransaktionDTO> transaktionen = gruppenRepo.gruppeTransaktionen(id);

    assertThat(transaktionen.get(0).sponsor()).isEqualTo("meh");

  }

  @Test
  @DisplayName("gruppenrepo stellt korrekt fest ob gruppe geschlossen ist")
  public void test_06() {
    Gruppe gruppe1 = new Gruppe("überfordert", "jeder");
    gruppenRepo.save(gruppe1);
    String id = gruppe1.id();

    service.closeGruppe(id);

    assertThat(gruppenRepo.isClosed(id)).isTrue();

  }

  @Test
  @DisplayName("getName übergibt korrekt den gruppennamen")
  public void test_07() {
    Gruppe gruppe1 = new Gruppe("überfordert", "jeder");
    gruppenRepo.save(gruppe1);
    String id = gruppe1.id();

    assertThat(gruppenRepo.getName(id)).isEqualTo("überfordert");

  }

  @Test
  @DisplayName("exists ist false wenn die gruppe nicht existiert")
  public void test_08() {
    String id = UUID.randomUUID().toString();

    assertThat(gruppenRepo.exists(id)).isFalse();

  }

  @Test
  @DisplayName("exists ist true wenn die gruppe existiert")
  public void test_09() {
    Gruppe gruppe1 = new Gruppe("überfordert", "jeder");
    gruppenRepo.save(gruppe1);
    String id = gruppe1.id();

    assertThat(gruppenRepo.exists(id)).isTrue();

  }

}

package de.propra.splitter.domain.service;

import de.propra.splitter.domain.model.Gruppe;
import de.propra.splitter.domain.service.GruppenService;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestGruppenService {
/*
  GruppenService gruppenService;

  @BeforeEach
  private void reset() {
    gruppenService = new GruppenService();
  }

  @Test
  @DisplayName("alle Gruppen eines Nutzers werden gefunden")
  void test_0(){

     String nutzer1 = "moaz";
     String nutzer2 ="nick";

    for (int i = 0; i <3 ; i++) {
      gruppenService.addGruppe("", nutzer1);
    }
      gruppenService.addGruppe("",nutzer2);

    Set<Gruppe> gefundeneGruppen = gruppenService.nutzerGruppen(nutzer1);

    assertThat(gefundeneGruppen).hasSize(3);

  }
   /*
  @Test
  @DisplayName("nur nach geschlossenen Gruppen suchen")
  void test_1(){
    String nutzer1 = "moaz";
    String nutzer2 ="nick";

    for (int i = 0; i <3 ; i++) {
      gruppenService.addGruppe("", nutzer1);
    }
    gruppenService.addGruppe("",nutzer2);

    Set<Gruppe> gefundeneGruppen = gruppenService.nutzerGruppen(nutzer1);

    assertThat(gefundeneGruppen).hasSize(3);
  }

  @Test
  @DisplayName("nur nach offenen Gruppen suchen")
  void test_2(){
    String nutzer1 = "moaz";
    Gruppe gruppe1 = new Gruppe(nutzer1);
    Gruppe gruppe2 = new Gruppe(nutzer1);

    gruppe2.close();
    HashSet<Gruppe> gruppen = new HashSet<>(Set.of(gruppe1, gruppe2));

    HashSet<Gruppe> open = gruppenService.offeneNutzerGruppen(gruppen, nutzer1);
    assertThat(open).containsExactly(gruppe1);
  }
*/
}

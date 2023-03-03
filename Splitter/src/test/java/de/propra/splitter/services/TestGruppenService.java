package de.propra.splitter.services;

import de.propra.splitter.domaene.Gruppe;
import de.propra.splitter.domaene.Nutzer;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestGruppenService {

  GruppenService gruppenService = new GruppenService();

  @Test
  @DisplayName("alle Gruppen eines Nutzers werden gefunden")
  void test_0(){

     Nutzer nutzer1 =new Nutzer("moaz");
     Nutzer nutzer2 =new Nutzer("nick");
     Set<Gruppe> gruppen =new HashSet<>();
     Set<Gruppe> nutzer1Gruppen =new HashSet<>();

    for (int i = 0; i <3 ; i++) {
      Gruppe g=new Gruppe(nutzer1);
      gruppen.add(g);
      nutzer1Gruppen.add(g);
    }
      gruppen.add(new Gruppe(nutzer2));

    Set<Gruppe> gefundeneGruppen = gruppenService.nutzerGruppen(gruppen, nutzer1);

    assertThat(nutzer1Gruppen).containsExactlyElementsOf(gefundeneGruppen);



  }
  @Test
  @DisplayName("nur nach geschlossenen Gruppen suchen")
  void test_1(){
    Nutzer nutzer1 =new Nutzer("moaz");
    Gruppe gruppe1 = new Gruppe(nutzer1);
    Gruppe gruppe2 = new Gruppe(nutzer1);

    gruppe2.close();
    HashSet<Gruppe> gruppen = new HashSet<>(Set.of(gruppe1, gruppe2));

    HashSet<Gruppe> closed = gruppenService.geschlosseneNutzerGruppen(gruppen, nutzer1);
    assertThat(closed).containsExactly(gruppe2);
  }

  @Test
  @DisplayName("nur nach offenen Gruppen suchen")
  void test_2(){
    Nutzer nutzer1 =new Nutzer("moaz");
    Gruppe gruppe1 = new Gruppe(nutzer1);
    Gruppe gruppe2 = new Gruppe(nutzer1);

    gruppe2.close();
    HashSet<Gruppe> gruppen = new HashSet<>(Set.of(gruppe1, gruppe2));

    HashSet<Gruppe> open = gruppenService.offeneNutzerGruppen(gruppen, nutzer1);
    assertThat(open).containsExactly(gruppe1);
  }

}

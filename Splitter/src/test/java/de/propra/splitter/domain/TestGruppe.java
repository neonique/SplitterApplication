package de.propra.splitter.domain;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGruppe {

  private String nutzer;
  @BeforeEach
  private void nutzerZuruecksetzen(){
    nutzer = "Moaz";
  }



  @Test()
  @DisplayName("erstelle Gruppe und fuege teilnehmer hinzu")
  void test_01(){
    String participant = "Nick";

    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    gruppe.addNutzer(participant);

    assertThat(gruppe.getTeilnehmerNamen()).contains(participant, nutzer);
  }
  @Test()
  @DisplayName("fuege transaktionen zu gruppe hinzu")
  void test_02(){
    //arrange
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants =Set.of("A","b");
    participants.forEach(gruppe::addNutzer);
    TransaktionDTO transaktionDTO = new TransaktionDTO(nutzer,participants,20.50,"");
    //act
    gruppe.addTransaktion(nutzer,participants,20.50,"");
    //assert
    assertThat(gruppe.getTransaktionenDetails().contains(transaktionDTO));

  }

  @Test
  @DisplayName("teilnehmer von transaktionen muessen teilnehmer von der gruppe sein")
  void test_03(){
    //arrange
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants = new HashSet<>(Set.of("A","b"));
    participants.forEach(gruppe::addNutzer);

    participants.add("Jeremy");
    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addTransaktion(nutzer,participants,20.50,"");
    });
    //assert
    assertThat("invalider nutzer in transaktion").isEqualTo(thrown.getMessage());
  }

  @Test
  @DisplayName("Transaktionen funktionieren auch, wenn nicht jedes Gruppenmitglied teilnimmt.")
  void test_04(){
    //arrange
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants = new HashSet<> ();
    String b = "B";
    participants.addAll(Set.of("A",b));
    participants.forEach(gruppe::addNutzer);
    participants.remove(b);
    TransaktionDTO transaktionDTO = new TransaktionDTO(nutzer,participants,20.50,"");
    //act
    gruppe.addTransaktion(nutzer,participants,20.50,"");
    //assert
    assertThat(gruppe.getTransaktionenDetails()).contains(transaktionDTO);
  }

  @Test()
  @DisplayName("Nutzer koennen nach der ersten Transaktion nicht mehr zur Gruppe hinzugefuegt werden.")
  void test_05(){
    //arrange
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants =Set.of("A","b");
    participants.forEach(gruppe::addNutzer);


    //act
    gruppe.addTransaktion(nutzer,participants,20.50,"");

    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      gruppe.addNutzer("Ellis");
    });
    //assert
    assertThat("Nutzer koennen nach der ersten Transaktion nicht mehr zur Gruppe hinzugefuegt werden.").isEqualTo(thrown.getMessage());

  }

  @Test()
  @DisplayName("Transaktionsbetraege muessen positiv sein.")
  void test_06(){
    //arrange
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants = Set.of("A", "b");
    participants.forEach(gruppe::addNutzer);

    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addTransaktion(nutzer,participants,-20.50,"");
    });
    //assert
    assertThat("Transaktionsbetraege muessen positiv sein.").isEqualTo(thrown.getMessage());

  }

  @Test()
  @DisplayName("Transaktionsbetraege koennen nicht Null sein")
  void test_07(){
    //arrange
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants = Set.of("A", "b");
    participants.forEach(gruppe::addNutzer);

    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addTransaktion(nutzer,participants,0,"");
    });
    //assert
    assertThat("Transaktionsbetraege muessen positiv sein.").isEqualTo(thrown.getMessage());

  }

  @Test
  @DisplayName("Nutzernamen muessen der GitHub-Namenskonvention entsprechen")
  void test_08(){

    String nutzer5 = new String("p3t-er");
    Gruppe gruppe = new Gruppe("gruppenName", nutzer5);


    IllegalArgumentException thrown0 = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addNutzer("");
    });
    IllegalArgumentException thrown1 = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addNutzer("-peter");
    });
    IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addNutzer("peter-");
    });
    IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addNutzer("pet--er");
    });
    IllegalArgumentException thrown4 = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addNutzer("pet*er");
    });

    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown0.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown1.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown2.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown3.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown4.getMessage());
  }

  @Test()
  @DisplayName("Gruppen koennen geschlossen werden")
  void test_09(){
    Gruppe g = new Gruppe("nutzerGruppe",nutzer);

    g.close();

    assertThat(g.isclosed());
  }

  @Test()
  @DisplayName("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden")
  void test_10(){
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants = Set.of("A", "b");
    participants.forEach(gruppe::addNutzer);

    gruppe.close();
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      gruppe.addTransaktion(nutzer, participants, 20,"");
    });

    assertThat("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden").isEqualTo(thrown.getMessage());
  }

  @Test()
  @DisplayName("Nutzer koennen nicht zu geschlossenen Gruppen hinzugefuegt werden")
  void test_11(){
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    String nutzer2 = new String("Jeremy");

    gruppe.close();
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      gruppe.addNutzer(nutzer2);
    });

    assertThat("Nutzer koennen nicht zu geschlossenen Gruppen hinzugefuegt werden").isEqualTo(thrown.getMessage());
  }

  @Test()
  @DisplayName("keine Transaktionen nur an sich selbst")
  void test_12(){
    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants =Set.of(nutzer);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addTransaktion(nutzer,participants,90,"");
    });

    assertThat("keine Transaktionen nur an sich selbst").isEqualTo(thrown.getMessage());
  }

  @Test()
  @DisplayName("Transaktionen muessen Bettler haben")
  void test_13(){

    Gruppe gruppe = new Gruppe("gruppenName", nutzer);
    Set<String> participants = new HashSet<>();

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.addTransaktion(nutzer,participants,90,"");
    });

    assertThat("Transaktionen muessen Bettler haben").isEqualTo(thrown.getMessage());
  }


}

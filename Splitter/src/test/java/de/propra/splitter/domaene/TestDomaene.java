package de.propra.splitter.domaene;

import java.util.HashSet;
import java.util.Set;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class TestDomaene {
  private Nutzer nutzer;
  @BeforeEach
  private void nutzerZuruecksetzen(){
    nutzer =new Nutzer("Moaz");
  }



  @Test()
  @DisplayName("erstelle Gruppe und fuege teilnehmer hinzu")
  void test_01(){
    Nutzer participant = new Nutzer("Nick");

    Gruppe gruppe = new Gruppe(nutzer);
    gruppe.nutzerHinzufuegen(participant);

    assertThat(gruppe.teilnehmer()).contains(participant, nutzer);
  }
  @Test()
  @DisplayName("fuege transaktionen zu gruppe hinzu")
  void test_02(){
    //arrange
    Gruppe gruppe = new Gruppe(nutzer);
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"));
    participants.forEach(gruppe::nutzerHinzufuegen);
    Money amount= Money.of(20.50 ,"EUR");
    Transaktion transaktion =new Transaktion(nutzer,participants,amount, "");
    //act
    gruppe.transaktionHinzufuegen(transaktion);
    //assert
    assertThat(gruppe.transaktionen()).contains(transaktion);

  }

  @Test
  @DisplayName("teilnehmer von transaktionen muessen teilnehmer von der gruppe sein")
  void test_03(){
    //arrange
    Gruppe gruppe = new Gruppe(nutzer);
    Set<Nutzer> participants = new HashSet<> ();
    participants.addAll(Set.of(new Nutzer("A"),new Nutzer("b")));
    participants.forEach(gruppe::nutzerHinzufuegen);
    Money amount= Money.of(20.50 ,"EUR");
    participants.add(new Nutzer("Jeremy"));
    Transaktion transaktion =new Transaktion(nutzer,participants,amount, "" );
    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.transaktionHinzufuegen(transaktion);
    });
    //assert
    assertThat("invalider nutzer in transaktion").isEqualTo(thrown.getMessage());
  }

  @Test
  @DisplayName("Transaktionen funktionieren auch, wenn nicht jedes Gruppenmitglied teilnimmt.")
  void test_04(){
    //arrange
    Gruppe gruppe = new Gruppe(nutzer);
    Set<Nutzer> participants = new HashSet<> ();
    Nutzer b = new Nutzer("B");
    participants.addAll(Set.of(new Nutzer("A"),b));
    participants.forEach(gruppe::nutzerHinzufuegen);
    Money amount= Money.of(20.50 ,"EUR");
    participants.remove(b);
    Transaktion transaktion =new Transaktion(nutzer,participants,amount, "");
    //act
    gruppe.transaktionHinzufuegen(transaktion);
    //assert
    assertThat(gruppe.transaktionen()).contains(transaktion);
  }

  @Test()
  @DisplayName("Nutzer koennen nach der ersten Transaktion nicht mehr zur Gruppe hinzugefuegt werden.")
  void test_05(){
    //arrange
    Gruppe gruppe = new Gruppe(nutzer);
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"));
    participants.forEach(gruppe::nutzerHinzufuegen);
    Money amount= Money.of(20.50 ,"EUR");
    Transaktion transaktion =new Transaktion(nutzer,participants,amount, "");

    //act
    gruppe.transaktionHinzufuegen(transaktion);

    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      gruppe.nutzerHinzufuegen(new Nutzer("Ellis"));
    });
    //assert
    assertThat("Nutzer koennen nach der ersten Transaktion nicht mehr zur Gruppe hinzugefuegt werden.").isEqualTo(thrown.getMessage());

  }

  @Test()
  @DisplayName("Transaktionsbetraege muessen positiv sein.")
  void test_06(){
    //arrange
    Gruppe gruppe = new Gruppe(nutzer);
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"));
    participants.forEach(gruppe::nutzerHinzufuegen);
    Money amount= Money.of(-20.50 ,"EUR");
    Transaktion transaktion =new Transaktion(nutzer,participants,amount, "");
    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.transaktionHinzufuegen(transaktion);
    });
    //assert
    assertThat("Transaktionsbetraege muessen positiv sein.").isEqualTo(thrown.getMessage());

  }

  @Test()
  @DisplayName("Transaktionsbetraege koennen nicht Null sein")
  void test_07(){
    //arrange
    Gruppe gruppe = new Gruppe(nutzer);
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"));
    participants.forEach(gruppe::nutzerHinzufuegen);
    Money amount= Money.of(0 ,"EUR");
    Transaktion transaktion =new Transaktion(nutzer,participants,amount, "");
    //act
    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      gruppe.transaktionHinzufuegen(transaktion);
    });
    //assert
    assertThat("Transaktionsbetraege muessen positiv sein.").isEqualTo(thrown.getMessage());

  }

  @Test
  @DisplayName("Nutzernamen muessen der GitHub-Namenskonvention entsprechen")
  void test_08(){

    Nutzer nutzer5 = new Nutzer("p3t-er");

    IllegalArgumentException thrown0 = assertThrows(IllegalArgumentException.class, () -> {
      Nutzer nutzer0 = new Nutzer("");
    });
    IllegalArgumentException thrown1 = assertThrows(IllegalArgumentException.class, () -> {
      Nutzer nutzer1 = new Nutzer("-peter");
    });
    IllegalArgumentException thrown2 = assertThrows(IllegalArgumentException.class, () -> {
      Nutzer nutzer2 = new Nutzer("peter-");
    });
    IllegalArgumentException thrown3 = assertThrows(IllegalArgumentException.class, () -> {
      Nutzer nutzer3 = new Nutzer("pet--er");
    });
    IllegalArgumentException thrown4 = assertThrows(IllegalArgumentException.class, () -> {
      Nutzer nutzer4 = new Nutzer("pet*er");
    });

    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown0.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown1.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown2.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown3.getMessage());
    assertThat("Nutzername ist nicht konform mit GitHub-Namenskonvention").isEqualTo(thrown4.getMessage());
    assertThat(nutzer5.name()).isEqualTo("p3t-er");
  }

  @Test()
  @DisplayName("Gruppen koennen geschlossen werden")
  void test_09(){
    Gruppe g = new Gruppe(nutzer);

    g.schliessen();

    assertThat(g.istGeschlossen());
  }

  @Test()
  @DisplayName("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden")
  void test_10(){
    Gruppe gruppe = new Gruppe(nutzer);
    Money amount= Money.of(20 ,"EUR");
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"));
    participants.forEach(gruppe::nutzerHinzufuegen);
    Transaktion transaktion = new Transaktion(nutzer, participants, amount, "");

    gruppe.schliessen();
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      gruppe.transaktionHinzufuegen(transaktion);
    });

    assertThat("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden").isEqualTo(thrown.getMessage());



  }

  @Test()
  @DisplayName("Nutzer koennen nicht zu geschlossenen Gruppen hinzugefuegt werden")
  void test_11(){
    Gruppe gruppe = new Gruppe(nutzer);
    Nutzer nutzer2 = new Nutzer("Jeremy");

    gruppe.schliessen();
    RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
      gruppe.nutzerHinzufuegen(nutzer2);
    });

    assertThat("Nutzer koennen nicht zu geschlossenen Gruppen hinzugefuegt werden").isEqualTo(thrown.getMessage());
  }
  @Test()
  @DisplayName("zaehle Bettler einer Transaktion, wenn Sponsor kein Bettler ist richtig")
  void test_12(){
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"));
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"), "");

   assertThat(transaktion.zaehleBettler()).isEqualTo(2);

  }

  @Test()
  @DisplayName("zaehle Bettler einer Transaktion, wenn Sponsor ein Bettler ist richtig")
  void test_12b(){
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"), nutzer);
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"), "");

    assertThat(transaktion.zaehleBettler()).isEqualTo(3);
  }
  @Test()
  @DisplayName("ueberpruefe ob Nutzer Teilnehmer der Transaktion ist ")
  void test_13(){
    Nutzer nutzer2 =new Nutzer("c");
    Nutzer nutzer3 =new Nutzer("a");
    Set<Nutzer> participants =Set.of(new Nutzer("b"), nutzer3);
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"), "");

    assertThat(transaktion.istTeilnehmer(nutzer));
    assertThat(transaktion.istTeilnehmer(nutzer3));
    assertThat(transaktion.istTeilnehmer(nutzer2)).isFalse();

  }
  @Test()
  @DisplayName("ueberpruefe ob Nutzer Sponsor der Transaktion ist")
  void test_14(){
    Nutzer nutzer2 =new Nutzer("c");
    Nutzer nutzer3 =new Nutzer("a");
    Set<Nutzer> participants =Set.of(new Nutzer("b"), nutzer3);
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"), "");

    assertThat(transaktion.istSponsor(nutzer));
    assertThat(transaktion.istSponsor(nutzer3)).isFalse();
    assertThat(transaktion.istSponsor(nutzer2)).isFalse();

  }
  @Test()
  @DisplayName("ueberpruefe ob Nutzer Bettler der Transaktion ist")
  void test_15(){
    Nutzer nutzer2 =new Nutzer("c");
    Nutzer nutzer3 =new Nutzer("a");
    Set<Nutzer> participants =Set.of(new Nutzer("b"), nutzer3);
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"), "");

    assertThat(transaktion.istBettler(nutzer)).isFalse();
    assertThat(transaktion.istBettler(nutzer3));
    assertThat(transaktion.istBettler(nutzer2)).isFalse();

  }

  @Test()
  @DisplayName("keine Transaktionen nur an sich selbst")
  void test_16(){

    Set<Nutzer> participants =Set.of(nutzer);

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"), "");;
    });

    assertThat("keine Transaktionen nur an sich selbst").isEqualTo(thrown.getMessage());
  }

  @Test()
  @DisplayName("Transaktionen muessen Bettler haben")
  void test_17(){

    Set<Nutzer> participants = new HashSet<>();

    IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
      Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"), "");;
    });

    assertThat("Transaktionen muessen Bettler haben").isEqualTo(thrown.getMessage());
  }

}
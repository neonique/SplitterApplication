package de.propra.splitter.domain.model.calculation;

import static org.assertj.core.api.Assertions.assertThat;

import de.propra.splitter.domain.model.Gruppe;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestTransaktionenBerechnung {
/*
  TransaktionenBerechnung transaktionsService = new EinfacherTransaktionenBerechnung();
  @DisplayName("Saldo wird korrekt berechnet, wenn Sponsor kein Bettler ist")
  @Test
  void test_01(){
    Nutzer nutzer1 = new Nutzer("Josch");
    Nutzer nutzer2 = new Nutzer("ellis");
    Gruppe gruppe = new Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);
    Money m1 = "EUR 20");
    gruppe.addTransaktion(nutzer1, Set.of(nutzer2), m1);

    Money u1Saldo = transaktionsService.berechneNutzerSaldo(nutzer1, gruppe);
    Money u2Saldo = transaktionsService.berechneNutzerSaldo(nutzer2, gruppe);

    assertThat(u1Saldo).isEqualTo("EUR 20"));
    assertThat(u2Saldo).isEqualTo("EUR -20"));
  }
  @DisplayName("Saldo wird korrekt berechnet, wenn Sponsor ein Bettler ist")
  @Test
  void test_02(){
    Nutzer nutzer1 = new Nutzer("Josch");
    Nutzer nutzer2 = new Nutzer("ellis");
    gruppe = new Gruppe Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);
    Money m1 = "EUR 20");
    gruppe.addTransaktion(nutzer1, Set.of(nutzer2, nutzer1), m1);

    Money u1Saldo = transaktionsService.berechneNutzerSaldo(nutzer1, gruppe);
    Money u2Saldo = transaktionsService.berechneNutzerSaldo(nutzer2, gruppe);

    assertThat(u1Saldo).isEqualTo("EUR 10"));
    assertThat(u2Saldo).isEqualTo("EUR -10"));
  }
*/
  @DisplayName("jens test 1")
  @Test
  void test_03(){
    String nutzer1 = "a";
    String nutzer2 = "b";
    Gruppe gruppe = new Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);

    gruppe.addTransaktion(nutzer1, Set.of(nutzer2, nutzer1), 10);
    gruppe.addTransaktion(nutzer1, Set.of(nutzer2, nutzer1), 20);

    HashMap<String, HashMap<String, String>> balance =  gruppe.notwendigeTransaktionen();

    assertThat(balance.get(nutzer1)).isNull();
    Entry<String, String> entry = Map.entry(nutzer1,"EUR -15.00");
    assertThat(balance.get(nutzer2)).containsExactly(entry);
  }

  @DisplayName("jens test 2")
  @Test
  void test_04(){
   String nutzer1 = "a";
    String nutzer2 = "b";
    Gruppe gruppe = new Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);

    gruppe.addTransaktion(nutzer1, Set.of(nutzer2, nutzer1), 10);
    gruppe.addTransaktion(nutzer2, Set.of(nutzer2, nutzer1), 20);

    HashMap<String, HashMap<String, String>> balance =  gruppe.notwendigeTransaktionen();

    assertThat(balance.get(nutzer2)).isNull(); //Empty-> Null
    Entry<String, String> entry = Map.entry(nutzer2, "EUR -5.00");
    assertThat(balance.get(nutzer1)).containsExactly(entry);
  }

  @DisplayName("jens test 3")
  @Test
  void test_05(){
   String nutzer1 = "a";
    String nutzer2 = "b";
    Gruppe gruppe = new Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);

    gruppe.addTransaktion(nutzer1, Set.of(nutzer2), 10);
    gruppe.addTransaktion(nutzer1, Set.of(nutzer2, nutzer1), 20);

    HashMap<String, HashMap<String, String>> balance =  gruppe.notwendigeTransaktionen();

    assertThat(balance.get(nutzer1)).isNull(); //Empty-> Null
    Entry<String, String> entry = Map.entry(nutzer1, "EUR -20.00");
    assertThat(balance.get(nutzer2)).containsExactly(entry);
  }


  @DisplayName("jens test 4")
  @Test
  void test_06(){
   String nutzer1 = "a";
    String nutzer2 = "b";
    String nutzer3 = "c";
    Gruppe gruppe = new Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);
    gruppe.addNutzer(nutzer3);

    gruppe.addTransaktion(nutzer1, Set.of(nutzer2, nutzer1), 10);
    gruppe.addTransaktion(nutzer2, Set.of(nutzer2, nutzer3), 10);
    gruppe.addTransaktion(nutzer3, Set.of(nutzer3, nutzer1), 10);

    HashMap<String, HashMap<String, String>> balance =  gruppe.notwendigeTransaktionen();

    assertThat(balance.get(nutzer1)).isNull(); //Empty-> Null
    assertThat(balance.get(nutzer2)).isNull(); //Empty-> Null
    assertThat(balance.get(nutzer3)).isNull(); //Empty-> Null
  }

  @DisplayName("jens test 5")
  @Test
  void test_07() {
   String nutzer1 = "a";
    String nutzer2 = "b";
    String nutzer3 = "c";
    Gruppe gruppe = new Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);
    gruppe.addNutzer(nutzer3);

    gruppe.addTransaktion(nutzer1, Set.of(nutzer1, nutzer2, nutzer3), 60);
    gruppe.addTransaktion(nutzer2, Set.of(nutzer1, nutzer2, nutzer3), 30);
    gruppe.addTransaktion(nutzer3, Set.of(nutzer2, nutzer3), 100);

    HashMap<String, HashMap<String, String>> balance = gruppe.notwendigeTransaktionen();

    assertThat(balance.get(nutzer1)).isNull(); //Empty-> Null
    //In den Lösungen sind folgende Entries getauscht, so ist es aber eigentlich richtig
    Entry<String, String> entry1 = Map.entry(nutzer1,  "EUR -30.00");
    Entry<String, String> entry2 = Map.entry(nutzer3, "EUR -20.00");
    assertThat(balance.get(nutzer2)).containsExactly(entry1,entry2);
    assertThat(balance.get(nutzer3)).isNull(); //Empty-> Null
  }

  @DisplayName("jens test 6")
  @Test
  void test_08() {
   String nutzer1 = "a";
    String nutzer2 = "b";
    String nutzer3 = "c";
    String nutzer4 = "d";
    String nutzer5 = "e";
    String nutzer6 = "f";
    Gruppe gruppe = new Gruppe("gruppenName", nutzer1);
    gruppe.addNutzer(nutzer2);
    gruppe.addNutzer(nutzer3);
    gruppe.addNutzer(nutzer4);
    gruppe.addNutzer(nutzer5);
    gruppe.addNutzer(nutzer6);
    gruppe.addTransaktion(nutzer1, Set.of(nutzer1, nutzer2, nutzer3, nutzer4, nutzer5, nutzer6), 564);
    gruppe.addTransaktion(nutzer2, Set.of(nutzer2, nutzer1), 38.58);
    gruppe.addTransaktion(nutzer2, Set.of(nutzer2, nutzer1, nutzer4), 38.58);
    gruppe.addTransaktion(nutzer3, Set.of(nutzer3, nutzer5, nutzer6), 82.11);
    gruppe.addTransaktion( nutzer4, Set.of(nutzer1, nutzer2, nutzer3, nutzer4, nutzer5, nutzer6), 96);
    gruppe.addTransaktion(nutzer6, Set.of(nutzer2, nutzer5, nutzer6), 95.37);

    HashMap<String, HashMap<String, String>> balance = gruppe.notwendigeTransaktionen();

    assertThat(balance.get(nutzer1)).isNull(); //Empty-> Null

    Entry<String, String> entry1 = Map.entry(nutzer1, "EUR -96.78");
    assertThat(balance.get(nutzer2)).containsExactly(entry1);
    Entry<String, String> entry2 = Map.entry(nutzer1, "EUR -55.26");
    assertThat(balance.get(nutzer3)).containsExactly(entry2);
    Entry<String, String> entry3 = Map.entry(nutzer1, "EUR -26.86");
    assertThat(balance.get(nutzer4)).containsExactly(entry3);
    Entry<String, String> entry4 = Map.entry(nutzer1, "EUR -169.16");
    assertThat(balance.get(nutzer5)).containsExactly(entry4);
    Entry<String, String> entry5 = Map.entry(nutzer1, "EUR -73.79");
    assertThat(balance.get(nutzer6)).containsExactly(entry5);
  }

  /*
  @DisplayName("jens test 7: minimal")
  @Test
  void test_09() {
    String user1 = new String("a");
    String user2 = new String("b");
    String user3 = new String("c");
    String user4 = new String("d");
    String user5 = new String("e");
    String user6 = new String("f");
    String user7 = new String("g");
    Gruppe group = new Gruppe("gruppenName", user1);
    group.addNutzer(user2);
    group.addNutzer(user3);
    group.addNutzer(user4);
    group.addNutzer(user5);
    group.addNutzer(user6);
    group.addNutzer(user7);

    group.addTransaktion(user4, Set.of(user4,user6), 20);
    group.addTransaktion(user7, Set.of(user2), 10);
    group.addTransaktion(user5, Set.of(user1, user3, user5), 75, " ");
    group.addTransaktion(user6, Set.of(user1, user6), 50);
    group.addTransaktion(user5, Set.of(user4), 40);
    group.addTransaktion(user6, Set.of(user2, user6), 40);
    group.addTransaktion(user6, Set.of(user3), 5);
    group.addTransaktion(user7, Set.of(user1), 30);

    HashMap<String, HashMap<String, String>> balance = group.notwendigeTransaktionen();

    balance.forEach((u,b) -> System.out.println(u + "pays: " + b.toString()));

    //Assert for Minimal
    Entry<String, String> entry1 = Map.entry(user1, "EUR -40");
    Entry<String, String> entry2 = Map.entry(user1, "EUR -40");
    assertThat(balance.get(user1)).containsExactly(entry1, entry2);
    Entry<String, String> entry3 = Map.entry(user1, "EUR -30");
    assertThat(balance.get(user2)).containsExactly(entry3);
    Entry<String, String> entry4 = Map.entry(user1, "EUR -30");
    assertThat(balance.get(user3)).containsExactly(entry4);
    Entry<String, String> entry5 = Map.entry(user1, "EUR -30");
    assertThat(balance.get(user4)).containsExactly(entry5);
  }
      */
}

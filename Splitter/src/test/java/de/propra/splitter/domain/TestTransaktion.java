package de.propra.splitter.domain;

import java.util.Set;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
public class TestTransaktion {
 
  Nutzer nutzer;
  
  @BeforeEach
  void reset(){
    nutzer = new Nutzer("Moaz");
  }
  
  @Test()
  @DisplayName("zaehle Bettler einer Transaktion, wenn Sponsor kein Bettler ist richtig")
  void test_1(){
    Set<Nutzer> participants = Set.of(new Nutzer("A"), new Nutzer("b"));
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"),"");

   assertThat(transaktion.countBettler()).isEqualTo(2);
  }

  @Test()
  @DisplayName("zaehle Bettler einer Transaktion, wenn Sponsor ein Bettler ist richtig")
  void test_2(){
    Set<Nutzer> participants =Set.of(new Nutzer("A"),new Nutzer("b"), nutzer);
    Transaktion transaktion =new Transaktion(nutzer,participants, Money.of(90,"EUR"),"");

    assertThat(transaktion.countBettler()).isEqualTo(3);
  }
  @Test()
  @DisplayName("ueberpruefe ob Nutzer Teilnehmer der Transaktion ist ")
  void test_3(){
    Nutzer nutzer2 =new Nutzer("c");
    Nutzer nutzer3 =new Nutzer("a");
    Set<Nutzer> participants =Set.of(new Nutzer("b"), nutzer3);
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"),"");

    assertThat(transaktion.isTeilnehmer(nutzer));
    assertThat(transaktion.isTeilnehmer(nutzer3));
    assertThat(transaktion.isTeilnehmer(nutzer2)).isFalse();

  }
  @Test()
  @DisplayName("ueberpruefe ob Nutzer Sponsor der Transaktion ist")
  void test_4(){
    Nutzer nutzer2 =new Nutzer("c");
    Nutzer nutzer3 =new Nutzer("a");
    Set<Nutzer> participants =Set.of(new Nutzer("b"), nutzer3);
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"),"");

    assertThat(transaktion.isSponsor(nutzer));
    assertThat(transaktion.isSponsor(nutzer3)).isFalse();
    assertThat(transaktion.isSponsor(nutzer2)).isFalse();

  }
  @Test()
  @DisplayName("ueberpruefe ob String Bettler der Transaktion ist")
  void test_5(){
    Nutzer nutzer2 =new Nutzer("c");
    Nutzer nutzer3 =new Nutzer("a");
    Set<Nutzer> participants =Set.of(new Nutzer("b"), nutzer3);
    Transaktion transaktion =new Transaktion(nutzer,participants,Money.of(90,"EUR"),"");

    assertThat(transaktion.isBettler(nutzer)).isFalse();
    assertThat(transaktion.isBettler(nutzer3));
    assertThat(transaktion.isBettler(nutzer2)).isFalse();

  }

}

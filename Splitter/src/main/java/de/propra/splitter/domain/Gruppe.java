package de.propra.splitter.domain;

import org.javamoney.moneta.Money;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Gruppe {
  private boolean geschlossen =false;

  private HashSet<Nutzer> teilnehmer = new HashSet<>();
  private HashSet<Transaktion> transaktionen =new HashSet<>();

  public Gruppe(Nutzer nutzer) {
    teilnehmer.add(nutzer);
  }


  public void addNutzer(Nutzer teilnehmer) {
    if(!transaktionen.isEmpty()){
      throw new RuntimeException("Nutzer koennen nach der ersten Transaktion nicht mehr zur Gruppe hinzugefuegt werden.");
    }
    if(this.isclosed()){
      throw new RuntimeException("Nutzer koennen nicht zu geschlossenen Gruppen hinzugefuegt werden");
    }
    this.teilnehmer.add(teilnehmer);
  }

  Set<Nutzer> teilnehmer() {
    return teilnehmer;
  }

  public void addTransaktion(Nutzer sponsor,Set<Nutzer> bettler, Money betrag, String beschreibung) {
    if(!teilnehmer.containsAll(bettler)||!teilnehmer.contains(sponsor)){
      throw new IllegalArgumentException("invalider nutzer in transaktion");
    }
    if(betrag.isNegativeOrZero()){
      throw new IllegalArgumentException("Transaktionsbetraege muessen positiv sein.");
    }
    if(this.isclosed()){
      throw new RuntimeException("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden");
    }

    transaktionen.add(new Transaktion(sponsor, bettler, betrag, beschreibung));
  }
  Set<Transaktion> transaktionen() {
    return Set.copyOf(transaktionen);
  }

  public boolean containsNutzer(Nutzer nutzer) {
    return teilnehmer.contains(nutzer);
  }

  public boolean isclosed(){
    return geschlossen;
  }

  public HashMap<Nutzer, HashMap<Nutzer, Money>> NotwendigeTransaktionen(){
    TransaktionsService Transaktionenrechner = new EinfacherTransaktionsService();
    return Transaktionenrechner.berechneNotwendigeTransaktionen(this);
  }
  public void close(){
   geschlossen =true;
  }
}



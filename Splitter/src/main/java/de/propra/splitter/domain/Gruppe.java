package de.propra.splitter.domain;

import java.util.stream.Collectors;
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


  public void addNutzer(String name) {

    if(!validateName(name)){
      throw new IllegalArgumentException("Nutzername ist nicht konform mit GitHub-Namenskonvention");
    }

    Nutzer teilnehmer = new Nutzer(name);
    if(!transaktionen.isEmpty()){
      throw new RuntimeException("Nutzer koennen nach der ersten Transaktion nicht mehr zur Gruppe hinzugefuegt werden.");
    }
    if(this.isclosed()){
      throw new RuntimeException("Nutzer koennen nicht zu geschlossenen Gruppen hinzugefuegt werden");
    }
    this.teilnehmer.add(teilnehmer);
  }

  private boolean validateName(String name) {

    if(name.contains("--") || name.startsWith("-") || name.endsWith("-")){
      return false;
    }

    String validChars = "[A-Za-z0-9-]+";
    if(!name.matches(validChars)){
      return false;
    }

    return true;
  }

  Set<Nutzer> teilnehmer() {

    return Set.copyOf(teilnehmer);
  }

  public Set<String> getTeilnehmerNamen() {
    return teilnehmer.stream().map(t -> t.name()).collect(Collectors.toSet());
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

  public Set<TransaktionDTO> getTransaktionenData() {
      Set<TransaktionDTO> transaktionDTOS = transaktionen
          .stream()
          .map(t -> new TransaktionDTO(t.sponsor().name(), t.bettler().stream().map(b -> b.name()).collect(
              Collectors.toSet()), t.betrag().toString(), t.beschreibung())).collect(
              Collectors.toSet());
      return transaktionDTOS;
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



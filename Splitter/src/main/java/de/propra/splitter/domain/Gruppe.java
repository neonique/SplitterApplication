package de.propra.splitter.domain;


import de.propra.splitter.domain.calculation.EinfacherTransaktionenBerechnung;
import de.propra.splitter.domain.calculation.TransaktionenBerechnung;

import de.propra.splitter.stereotypes.AggregateRoot;

import java.util.UUID;
import java.util.stream.Collectors;
import org.javamoney.moneta.Money;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@AggregateRoot
public class Gruppe {


  private boolean geschlossen =false;

  private UUID id;

  private HashSet<Nutzer> teilnehmer = new HashSet<>();
  private HashSet<Transaktion> transaktionen =new HashSet<>();

  private final String name;
  public Gruppe(String name, String nutzerName) {
    Nutzer nutzer = new Nutzer(nutzerName);
    teilnehmer.add(nutzer);
    this.name = name;
    id = UUID.randomUUID();
  }

  public Gruppe(String name, String nutzerName, UUID id) {
    Nutzer nutzer = new Nutzer(nutzerName);
    teilnehmer.add(nutzer);
    this.name = name;
    this.id = id;
  }

  public Gruppe(Set<String> nutzerNamen){
    Set<Nutzer> nutzer = nutzerNamen.stream().map(n -> new Nutzer(n)).collect(Collectors.toSet());
    teilnehmer.addAll(nutzer);
    this.name = "remove later";
    id = UUID.randomUUID();
  }

  public String id() {
    return id.toString();
  }

  public String name(){
    return this.name;
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

  public Set<Nutzer> teilnehmer() {

    return Set.copyOf(teilnehmer);
  }

  public Set<String> getTeilnehmerNamen() {
    return teilnehmer.stream().map(t -> t.name()).collect(Collectors.toSet());
  }

  public void addTransaktion(String sponsor,Set<String> bettler, double betrag) {
    if(bettler.isEmpty()) {
      throw new IllegalArgumentException("Transaktionen muessen Bettler haben");
    }
    if(bettler.size() == 1 && bettler.contains(sponsor)) {
      throw new IllegalArgumentException("keine Transaktionen nur an sich selbst");
    }
    if(!this.getTeilnehmerNamen().containsAll(bettler)||!this.getTeilnehmerNamen().contains(sponsor)){
      throw new IllegalArgumentException("invalider nutzer in transaktion");
    }

    Money geld = Money.of(betrag, "EUR");

    if(geld.isNegativeOrZero()){
      throw new IllegalArgumentException("Transaktionsbetraege muessen positiv sein.");
    }
    if(this.isclosed()){
      throw new RuntimeException("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden");
    }

    transaktionen.add(new Transaktion(new Nutzer(sponsor), bettler.stream().map(b -> new Nutzer(b)).collect(
        Collectors.toSet()), geld));
  }

  public Set<TransaktionDTO> getTransaktionenDetails() {
      Set<TransaktionDTO> transaktionDTOS = transaktionen
          .stream()
          .map(t -> new TransaktionDTO(t.sponsor().name(), t.bettler()
              .stream()
              .map(b -> b.name())
              .collect(Collectors.toSet()), t.betrag().getNumber().doubleValueExact()))
          .collect(Collectors.toSet());
      return transaktionDTOS;
    }
  public Set<Transaktion> transaktionen() {
    return Set.copyOf(transaktionen);
  }

  public boolean containsNutzer(String nutzerName) {
    Nutzer nutzer = new Nutzer(nutzerName);
    return teilnehmer.contains(nutzer);
  }

  public boolean isclosed(){
    return geschlossen;
  }

  public HashMap<String, HashMap<String, String>> notwendigeTransaktionen(){
    TransaktionenBerechnung Transaktionenrechner = new EinfacherTransaktionenBerechnung();
    return Transaktionenrechner.berechneNotwendigeTransaktionen(this);

  }
  public void close(){
   geschlossen =true;
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
}


package de.propra.splitter.domain;


import java.util.Map;
import java.util.stream.Collectors;
import org.javamoney.moneta.Money;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Gruppe {
  private boolean geschlossen =false;

  private HashSet<Nutzer> teilnehmer = new HashSet<>();
  private HashSet<Transaktion> transaktionen =new HashSet<>();

  public Gruppe(String nutzerName) {
    Nutzer nutzer = new Nutzer(nutzerName);
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

  public void addTransaktion(String sponsor,Set<String> bettler, Money betrag, String beschreibung) {
    if(bettler.isEmpty()) {
      throw new IllegalArgumentException("Transaktionen muessen Bettler haben");
    }
    if(bettler.size() == 1 && bettler.contains(sponsor)) {
      throw new IllegalArgumentException("keine Transaktionen nur an sich selbst");
    }

    if(!teilnehmer.containsAll(bettler)||!teilnehmer.contains(sponsor)){
      throw new IllegalArgumentException("invalider nutzer in transaktion");
    }
    if(betrag.isNegativeOrZero()){
      throw new IllegalArgumentException("Transaktionsbetraege muessen positiv sein.");
    }
    if(this.isclosed()){
      throw new RuntimeException("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden");
    }

    transaktionen.add(new Transaktion(new Nutzer(sponsor), bettler.stream().map(b -> new Nutzer(b)).collect(
        Collectors.toSet()), betrag, beschreibung));
  }

  public Set<TransaktionDTO> getTransaktionenData() {
      Set<TransaktionDTO> transaktionDTOS = transaktionen
          .stream()
          .map(t -> new TransaktionDTO(t.sponsor().name(), t.bettler()
              .stream()
              .map(b -> b.name())
              .collect(Collectors.toSet()), t.betrag().toString(), t.beschreibung()))
          .collect(Collectors.toSet());
      return transaktionDTOS;
    }
  Set<Transaktion> transaktionen() {
    return Set.copyOf(transaktionen);
  }

  public boolean containsNutzer(String nutzerName) {
    Nutzer nutzer = new Nutzer(nutzerName);
    return teilnehmer.contains(nutzer);
  }

  public boolean isclosed(){
    return geschlossen;
  }

  public HashMap<String, HashMap<String, String>> NotwendigeTransaktionen(){
    TransaktionsService Transaktionenrechner = new EinfacherTransaktionsService();
    HashMap<Nutzer, HashMap<Nutzer, Money>> notwendigeTransaktionen = Transaktionenrechner.berechneNotwendigeTransaktionen(this);

    HashMap<String, HashMap<String, String>> notwendigeTransaktionenString = notwendigeTransaktionen
        .entrySet()
        .stream()
        .collect(Collectors.toMap(
            e -> e.getKey().name(),
            e -> e.getValue()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                    e2 -> e2.getKey().name(),
                    e2 -> e2.getValue().toString()
                ))
        ));


    return notwendigeTransaktionenString;
  }
  public void close(){
   geschlossen =true;
  }
}



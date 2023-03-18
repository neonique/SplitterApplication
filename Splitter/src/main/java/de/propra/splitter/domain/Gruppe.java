package de.propra.splitter.domain;


import de.propra.splitter.domain.calculation.EinfacherTransaktionenBerechnung;
import de.propra.splitter.domain.calculation.TransaktionenBerechnung;

import de.propra.splitter.stereotypes.AggregateRoot;

import java.util.*;
import java.util.stream.Collectors;
import org.javamoney.moneta.Money;

@AggregateRoot
public class Gruppe {


  private boolean geschlossen =false;

  private UUID id;

  public Gruppe(boolean geschlossen, UUID id, HashSet<String> teilnehmerNamen,
      List<TransaktionDTO> transaktionenDtos, String name) {

    HashSet<Nutzer> teilnehmer = getNutzer(teilnehmerNamen);

    List<Transaktion> transaktionen = getTransaktionen(
        transaktionenDtos);

    this.geschlossen = geschlossen;
    this.id = id;
    this.teilnehmer = teilnehmer;
    this.transaktionen = transaktionen;
    this.name = name;
  }

  private HashSet<Nutzer> teilnehmer = new HashSet<>();
  private List<Transaktion> transaktionen =new ArrayList<>();

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

  public void addNutzer(String name){

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

  public void addTransaktion(String sponsor,Set<String> bettler, double betrag, String grund) {
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
        Collectors.toSet()), geld, grund));
  }

  public List<TransaktionDTO> getTransaktionenDetails() {
      List<TransaktionDTO> transaktionDTOS = transaktionen
          .stream()
          .map(t -> new TransaktionDTO(t.id(), t.sponsor().name(), t.bettler()
              .stream()
              .map(b -> b.name())
              .collect(Collectors.toSet()), t.betrag().getNumberStripped().doubleValue(), t.grund()))
          .collect(Collectors.toList());
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

  public HashMap<String, HashMap<String, Double>> notwendigeTransaktionen(){
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

  private List<Transaktion> getTransaktionen(List<TransaktionDTO> transaktionenDtos) {
    List<Transaktion> transaktionen = new ArrayList<>();

    for (TransaktionDTO dto: transaktionenDtos) {
      Nutzer nutzer = new Nutzer(dto.sponsor());
      Set<Nutzer> bettler = new HashSet<>();
      for (String b: dto.bettler()
      ) {
        bettler.add(new Nutzer(b));
      }
      transaktionen.add(new Transaktion(dto.id(), nutzer, bettler, Money.of(dto.betrag(), "EUR"), dto.grund()));
    }
    return transaktionen;
  }

  private HashSet<Nutzer> getNutzer(HashSet<String> teilnehmerNamen) {
    HashSet<Nutzer> teilnehmer = new HashSet<>();

    for (String t: teilnehmerNamen
    ) {
      teilnehmer.add(new Nutzer(t));
    }
    return teilnehmer;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Gruppe gruppe = (Gruppe) o;
    return Objects.equals(id, gruppe.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}



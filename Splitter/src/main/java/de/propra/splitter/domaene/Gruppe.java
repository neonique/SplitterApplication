package de.propra.splitter.domaene;

import java.util.HashSet;
import java.util.Set;

public class Gruppe {
  private boolean geschlossen =false;

  private HashSet<Nutzer> teilnehmer = new HashSet<>();
  private HashSet<Transaktion> transaktionen =new HashSet<>();

  public Gruppe(Nutzer nutzer) {
    teilnehmer.add(nutzer);
  }


  public void nutzerHinzufuegen(Nutzer teilnehmer) {
    if(!transaktionen.isEmpty()){
      throw new RuntimeException("Nutzer koennen nach der ersten Transaktion nicht mehr zur Gruppe hinzugefuegt werden.");
    }
    if(this.istGeschlossen()){
      throw new RuntimeException("Nutzer koennen nicht zu geschlossenen Gruppen hinzugefuegt werden");
    }
    this.teilnehmer.add(teilnehmer);
  }

  public Set<Nutzer> teilnehmer() {
    return teilnehmer;
  }

  public void transaktionHinzufuegen(Transaktion transaktion) {
    if(!teilnehmer.containsAll(transaktion.bettler())){
      throw new IllegalArgumentException("invalider nutzer in transaktion");
    }
    if(transaktion.betrag().isNegativeOrZero()){
      throw new IllegalArgumentException("Transaktionsbetraege muessen positiv sein.");
    }
    if(this.istGeschlossen()){
      throw new RuntimeException("Transaktionen koennen nicht zu geschlossenen Gruppen hinzugefuegt werden");
    }

    transaktionen.add(transaktion);
  }
  public Set<Transaktion> transaktionen(){
    return transaktionen;
  }
  public boolean enthaeltNutzer(Nutzer nutzer) {
    return teilnehmer.contains(nutzer);
  }

  public boolean istGeschlossen(){
    return geschlossen;
  }
  public void schliessen(){
   geschlossen =true;
  }
}



package de.propra.splitter.domain.service;

import de.propra.splitter.domain.model.Gruppe;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.javamoney.moneta.Money;

import de.propra.splitter.domain.model.TransaktionDTO;
import org.springframework.stereotype.Service;

import javax.money.MonetaryAmount;

@Service
public class GruppenService {
    //Zum Testen
    private Gruppe gruppe;



  private void createGruppe(String nutzerName){
      gruppe = new Gruppe("a", nutzerName);
  }

  private void addNutzer(Set<String> nutzerSet){
      int i = 0;
      for (String nutzer : nutzerSet) {
          if(i == 0){
              createGruppe(nutzer);
              i++;
          }
          else{
              gruppe.addNutzer(nutzer);
          }

      }

  }

  private void addTransaction(Set<TransaktionDTO> transaktionDTOSet){
      double betrag;
      for(TransaktionDTO transaktionDTO : transaktionDTOSet){
          //je nach dem wie der betrag geschrieben ist muss mehr gemacht werden!
          betrag = Double.parseDouble(transaktionDTO.betrag());
          gruppe.addTransaktion(transaktionDTO.sponsor(),transaktionDTO.bettler(), betrag);
      }
  }

  public HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen
          (HashSet<String> nutzer, HashSet<TransaktionDTO> transaktionDTOs){
      this.addNutzer(nutzer);
      this.addTransaction(transaktionDTOs);
      return gruppe.notwendigeTransaktionen();
  }



}

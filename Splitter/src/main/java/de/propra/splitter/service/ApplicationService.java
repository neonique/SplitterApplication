package de.propra.splitter.service;

import de.propra.splitter.domain.model.Gruppe;
import java.util.HashMap;
import java.util.HashSet;

import de.propra.splitter.domain.model.TransaktionDTO;
import de.propra.splitter.domain.service.GruppenService;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
  GruppenService gruppenService;


  @Autowired
  public ApplicationService(GruppenService gruppenService){
    this.gruppenService = gruppenService;
  }
  public int addGruppe(String gruppenName, String nutzerName){
    return 0;
  }


  public HashMap<Integer, String> nutzerGruppen(String nutzerName){
    return new HashMap<>();
  }


  public HashMap<Integer, String> offeneNutzerGruppen(String nutzerName){
    return new HashMap<>();
  }

  public HashMap<Integer, String> geschlosseneNutzerGruppen(String nutzerName){
    return new HashMap<>();
  }


  public void closeGruppe(int id) {
    return;
  }
/*
  public HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen(int gruppenID){
    HashSet<String> nutzer = new HashSet<>();
    HashSet<TransaktionDTO> transaktionDTOs = new HashSet<>();

    return gruppenService.berechneNotwendigeTransaktionen(nutzer, transaktionDTOs);
  }
*/
  public void addNutzerToGruppe(int id, String nutzerName) {

  }

  public int transaktionHinzufuegen(int gruppenId, TransaktionDTO transaktion ) {
    return 0;
  }

  public Set<String> getGruppenNutzer(int gruppenId) {
    return new HashSet<>();
  }

  public Set<TransaktionDTO> getGruppenTransaktionen(int gruppenId) {
    return new HashSet<>();
  }
  public HashMap<String, HashMap<String, String>> berechneNotwendigeTransaktionen
      (HashSet<String> nutzer, HashSet<TransaktionDTO> transaktionDTOs) {

    Gruppe gruppe = new Gruppe(nutzer);

    for (TransaktionDTO transaktionDTO : transaktionDTOs) {
      //je nach dem wie der betrag geschrieben ist muss mehr gemacht werden!
      gruppe.addTransaktion(transaktionDTO.sponsor(), transaktionDTO.bettler(), transaktionDTO.betrag());
    }

    return gruppe.notwendigeTransaktionen();
  }



}

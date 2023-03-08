package de.propra.splitter.applicationservice;

import de.propra.splitter.domain.model.Gruppe;

import de.propra.splitter.domain.model.Nutzer;
import de.propra.splitter.persistence.GruppenRepository;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import de.propra.splitter.domain.service.GruppenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {
  GruppenService gruppenService;
  GruppenRepository gruppenRepository;

  @Autowired
  public ApplicationService(GruppenService gruppenService, GruppenRepository gruppenRepository){
    this.gruppenService = gruppenService;
    this.gruppenRepository = gruppenRepository;
  }
  public int addGruppe(String gruppenName, String nutzerName){
    return gruppenService.addGruppe(gruppenName, nutzerName);
  }


  public HashMap<Integer, String> nutzerGruppen(String nutzerName){
    return gruppenService.nutzerGruppen(nutzerName);
  }


  public HashMap<Integer, String> offeneNutzerGruppen(String nutzerName){
    return gruppenService.offeneNutzerGruppen(nutzerName);
  }

  public HashMap<Integer, String> geschlosseneNutzerGruppen(String nutzerName){
    return gruppenService.geschlosseneNutzerGruppen(nutzerName);
  }

  //braucht id
  public void closeGruppe(int id) {
    gruppenService.closeGruppe(id);
  }

}

package de.propra.splitter.applicationservice;

import de.propra.splitter.domain.model.Gruppe;

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
  @Autowired
  public ApplicationService(GruppenService gruppenService){
    this.gruppenService = gruppenService;
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

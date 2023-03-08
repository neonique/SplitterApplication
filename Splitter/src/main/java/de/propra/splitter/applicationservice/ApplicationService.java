package de.propra.splitter.applicationservice;

import de.propra.splitter.domain.model.Gruppe;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

  private HashSet<Gruppe> gruppen = new HashSet<Gruppe>();


  public HashSet<Gruppe> getGruppen(){

    return gruppen;
  }

  public void addGruppe(String gruppenName, String nutzerName){
    gruppen.add(new Gruppe(gruppenName, nutzerName));
  }



  public HashSet<Gruppe> nutzerGruppen(String nutzerName){
    return new HashSet<Gruppe>(gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).collect(
        Collectors.toSet()));
  }


  public HashSet<Gruppe> offeneNutzerGruppen(String nutzerName){
    return new HashSet<Gruppe>(
        gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).filter(Predicate.not(Gruppe::isclosed)).collect(Collectors.toSet()));
  }

  public HashSet<Gruppe> geschlosseneNutzerGruppen(String nutzerName){
    return new HashSet<Gruppe>(
        gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).filter(Gruppe::isclosed).collect(Collectors.toSet()));
  }

  //braucht id

}

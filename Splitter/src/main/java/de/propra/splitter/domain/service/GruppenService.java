package de.propra.splitter.domain.service;

import de.propra.splitter.domain.model.Gruppe;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GruppenService {
    //Zum Testen
    private HashSet<Gruppe> gruppen = new HashSet<Gruppe>();


  public HashSet<Gruppe> getGruppen(){

    return gruppen;
  }

  public void addGruppe(String gruppenName, String nutzerName){
      gruppen.add(new Gruppe(gruppenName, nutzerName));
  }



  public HashSet<Gruppe> nutzerGruppen(String nutzerName){
      return new HashSet<Gruppe>(gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).collect(Collectors.toSet()));
  }


  public HashSet<Gruppe> offeneNutzerGruppen(String nutzerName){
      return new HashSet<Gruppe>(
          gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).filter(Predicate.not(Gruppe::isclosed)).collect(Collectors.toSet()));
  }

   public HashSet<Gruppe> geschlosseneNutzerGruppen(String nutzerName){
      return new HashSet<Gruppe>(
          gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).filter(Gruppe::isclosed).collect(Collectors.toSet()));
  }
  private Gruppe findById(int id){
    return gruppen.stream().filter(t-> t.id() == id).findFirst().orElse(null);

  }

//braucht id
  public void closeGruppe(int id) {
    Gruppe gruppe=findById(id);
    gruppe.close();
  }

}

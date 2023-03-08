package de.propra.splitter.domain.service;

import de.propra.splitter.domain.model.Gruppe;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GruppenService {
    //Zum Testen
    private HashSet<Gruppe> gruppen = new HashSet<Gruppe>();



  public int addGruppe(String gruppenName, String nutzerName){
      Gruppe gruppe = new Gruppe(gruppenName, nutzerName);
      gruppen.add(gruppe);
      return gruppe.id();
  }




  public HashMap<Integer, String> nutzerGruppen(String nutzerName){
      return new HashMap<Integer, String>(gruppen
              .stream()
              .filter(group -> group.containsNutzer(nutzerName))
              .collect(Collectors.toMap(e-> e.id(), e-> e.name())));
  }


  public HashMap<Integer, String> offeneNutzerGruppen(String nutzerName){
      return new HashMap<Integer, String>(gruppen
              .stream()
              .filter(group -> group.containsNutzer(nutzerName))
              .filter(Predicate.not(Gruppe::isclosed))
              .collect(Collectors.toMap(e-> e.id(), e-> e.name())));
  }

   public HashMap<Integer, String> geschlosseneNutzerGruppen(String nutzerName){
       return new HashMap<Integer, String>(gruppen
               .stream()
               .filter(group -> group.containsNutzer(nutzerName))
               .filter(Gruppe::isclosed)
               .collect(Collectors.toMap(e-> e.id(), e-> e.name())));
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

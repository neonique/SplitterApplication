package de.propra.splitter.services;

import de.propra.splitter.domain.Gruppe;
import de.propra.splitter.domain.Nutzer;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GruppenService {

  public HashSet<Gruppe> nutzerGruppen(Set<Gruppe> gruppen, String nutzerName){
      return new HashSet<Gruppe>(gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).collect(Collectors.toSet()));
  }


  public HashSet<Gruppe> offeneNutzerGruppen(Set<Gruppe> gruppen, String nutzerName){
      return new HashSet<Gruppe>(
          gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).filter(Predicate.not(Gruppe::isclosed)).collect(Collectors.toSet()));
  }

   public HashSet<Gruppe> geschlosseneNutzerGruppen(Set<Gruppe> gruppen, String nutzerName){
      return new HashSet<Gruppe>(
          gruppen.stream().filter(group -> group.containsNutzer(nutzerName)).filter(Gruppe::isclosed).collect(Collectors.toSet()));
  }

}

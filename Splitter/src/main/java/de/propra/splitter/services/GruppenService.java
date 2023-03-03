package de.propra.splitter.services;

import de.propra.splitter.domaene.Gruppe;
import de.propra.splitter.domaene.Nutzer;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GruppenService {

  public HashSet<Gruppe> nutzerGruppen(Set<Gruppe> gruppen, Nutzer nutzer){
      return new HashSet<Gruppe>(gruppen.stream().filter(group -> group.enthaeltNutzer(nutzer)).collect(Collectors.toSet()));
  }


  public HashSet<Gruppe> offeneNutzerGruppen(Set<Gruppe> gruppen, Nutzer nutzer){
      return new HashSet<Gruppe>(
          gruppen.stream().filter(group -> group.enthaeltNutzer(nutzer)).filter(Predicate.not(Gruppe::istGeschlossen)).collect(Collectors.toSet()));
  }

   public HashSet<Gruppe> geschlosseneNutzerGruppen(Set<Gruppe> gruppen, Nutzer nutzer){
      return new HashSet<Gruppe>(
          gruppen.stream().filter(group -> group.enthaeltNutzer(nutzer)).filter(Gruppe::istGeschlossen).collect(Collectors.toSet()));
  }

}

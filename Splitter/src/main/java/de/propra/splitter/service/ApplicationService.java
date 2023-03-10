package de.propra.splitter.service;

import de.propra.splitter.domain.Gruppe;
import java.util.HashMap;

import de.propra.splitter.domain.TransaktionDTO;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {


  private GruppenRepo gruppenRepo;
  @Autowired
  public ApplicationService(GruppenRepo gruppenRepo){
    this.gruppenRepo = gruppenRepo;
  }
  public String addGruppe(String gruppenName, String nutzerName){
    Gruppe gruppe = new Gruppe(gruppenName, nutzerName);
    gruppenRepo.save(gruppe);
    return gruppe.Id();
  }


  public HashMap<String, String> nutzerGruppen(String nutzerName){
   Set<Gruppe> gruppen = gruppenRepo.nutzerGruppen(nutzerName);
   HashMap<String, String> nutzerGruppen = new HashMap<>();
   gruppen.stream().forEach(a -> nutzerGruppen.put(a.Id(), a.name()));
   return nutzerGruppen;
  }


  public HashMap<String, String> offeneNutzerGruppen(String nutzerName){
    Set<Gruppe> gruppen = gruppenRepo.nutzerGruppen(nutzerName);
    HashMap<String, String> nutzerGruppen = new HashMap<>();
    gruppen.stream().filter(a -> !a.isclosed()).forEach(a -> nutzerGruppen.put(a.Id(), a.name()));
    return nutzerGruppen;
  }

  public HashMap<String, String> geschlosseneNutzerGruppen(String nutzerName){
    Set<Gruppe> gruppen = gruppenRepo.nutzerGruppen(nutzerName);
    HashMap<String, String> nutzerGruppen = new HashMap<>();
    gruppen.stream().filter(a -> a.isclosed()).forEach(a -> nutzerGruppen.put(a.Id(), a.name()));
    return nutzerGruppen;
  }


  public void closeGruppe(String id) {
    Gruppe gruppe = gruppenRepo.load(id);
    gruppe.close();
    gruppenRepo.save(gruppe);
  }

  public HashMap<String, HashMap<String, String>> notwendigeTransaktionen(String id){
    Gruppe gruppe = gruppenRepo.load(id);

    return gruppe.notwendigeTransaktionen();
  }

  public void addNutzerToGruppe(String id, String nutzerName) {
    Gruppe gruppe = gruppenRepo.load(id);
    gruppe.addNutzer(nutzerName);
    gruppenRepo.save(gruppe);

  }

  public void addTransaktionToGruppe(String id, String sponsor, Set<String> bettler, double betrag ) {
    Gruppe gruppe = gruppenRepo.load(id);
    gruppe.addTransaktion(sponsor, bettler, betrag);
    gruppenRepo.save(gruppe);
  }

  public Set<String> getGruppenNutzer(String id) {
    return gruppenRepo.gruppeNutzer(id);
  }

  public Set<TransaktionDTO> getGruppenTransaktionen(String id) {
    return gruppenRepo.gruppeTransaktionen(id);
  }

}

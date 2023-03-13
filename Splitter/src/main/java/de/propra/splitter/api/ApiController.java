package de.propra.splitter.api;

import de.propra.splitter.api.answer.AusgabenDataAPI;
import de.propra.splitter.api.answer.GruppeBasicDataAPI;
import de.propra.splitter.api.answer.GruppenDataDetailedAPI;
import de.propra.splitter.api.answer.NutzerGruppenBasicDataAPI;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.service.ApplicationService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

  ApplicationService applicationService;

  @Autowired
  public ApiController(ApplicationService applicationService){
    this.applicationService = applicationService;
  }

  //works
  @PostMapping("/gruppen")
  public ResponseEntity<String> addGruppe(@RequestBody GruppeBasicDataAPI neueGruppe){
    String id = applicationService.addGruppe(neueGruppe.name(), neueGruppe.personen().poll());
    for (String nutzer: neueGruppe.personen()) {
      applicationService.addNutzerToGruppe(id, nutzer);
    }
    return new ResponseEntity<>(id, HttpStatus.CREATED);
  }

  //works
  @GetMapping("/user/{nutzername}/gruppen")
  public ResponseEntity<NutzerGruppenBasicDataAPI> getNutzerGruppen(@PathVariable String nutzername){
    LinkedList<GruppeBasicDataAPI> list = new LinkedList<>();

    HashMap<String, String> gruppen = applicationService.nutzerGruppen(nutzername);
    for(Map.Entry<String, String> gruppe : gruppen.entrySet()) {
      LinkedList<String> teilnehmer = new LinkedList<>(applicationService.getGruppenNutzer(gruppe.getKey()));
      GruppeBasicDataAPI data = new GruppeBasicDataAPI(gruppe.getKey(), gruppe.getValue(), teilnehmer);
      list.add(data);
    }
    NutzerGruppenBasicDataAPI gruppenData =  new NutzerGruppenBasicDataAPI(list);
    return new ResponseEntity<>(gruppenData, HttpStatus.OK);
  }


  @GetMapping("/user/gruppen/{id}")
  public ResponseEntity<GruppenDataDetailedAPI> getGruppenInfo(@PathVariable String id){

    LinkedList<AusgabenDataAPI> ausgaben = new LinkedList<>();
    Set<TransaktionDTO> gruppenTransaktionen = applicationService.getGruppenTransaktionen(id);
    for (TransaktionDTO transaktion : gruppenTransaktionen) {
      String grund = "BLANK";
      String glaeubiger = transaktion.sponsor();
      int cent = (int)(transaktion.betrag() * 100);
      LinkedList<String> schuldner = new LinkedList<>(transaktion.bettler());

      AusgabenDataAPI ausgabe = new AusgabenDataAPI(grund, glaeubiger, cent, schuldner);

      ausgaben.add(ausgabe);
    }

    GruppenDataDetailedAPI gruppenData = new GruppenDataDetailedAPI(id, applicationService.getName(id),
        new LinkedList<>(applicationService.getGruppenNutzer(id)), applicationService.isClosed(id),
        ausgaben);
    return new ResponseEntity<>(gruppenData, HttpStatus.OK);
  }
//works
  @PostMapping("/gruppen/{id}/schliessen")
  public ResponseEntity<String> closeGruppe(@PathVariable String id){
    if(!applicationService.exists(id)){
      return new ResponseEntity<>("Gruppe " + id + " nicht gefunden.", HttpStatus.NOT_FOUND);
    }
    applicationService.closeGruppe(id);
    return new ResponseEntity<>("Gruppe " + id + " geschlossen.", HttpStatus.OK);
  }

  @PostMapping("/gruppen/{id}/auslagen")
  public GruppeBasicDataAPI getTransaktionen(){
    return null;
  }
}
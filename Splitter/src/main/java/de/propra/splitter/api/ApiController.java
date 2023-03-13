package de.propra.splitter.api;

import de.propra.splitter.api.answer.GruppeBasicDataAPI;
import de.propra.splitter.api.answer.NutzerGruppenBasicDataAPI;
import de.propra.splitter.service.ApplicationService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
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
    return new ResponseEntity<>(gruppenData, HttpStatus.CREATED);
  }


  @GetMapping("/user/gruppen/{id}")
  public GruppeBasicDataAPI getGruppenInfo(@PathVariable String id){


    return null;
  }

  @PostMapping("/gruppen/{id}/schliessen")
  public GruppeBasicDataAPI closeGruppe(){
    return null;
  }

  @PostMapping("/gruppen/{id}/auslagen")
  public GruppeBasicDataAPI getTransaktionen(){
    return null;
  }
}

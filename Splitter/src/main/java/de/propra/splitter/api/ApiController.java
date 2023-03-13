package de.propra.splitter.api;

import de.propra.splitter.api.answer.AusgabenDataAPI;
import de.propra.splitter.api.answer.AusgleichDataAPI;
import de.propra.splitter.api.answer.GruppeBasicDataAPI;
import de.propra.splitter.api.answer.GruppenDataDetailedAPI;
import de.propra.splitter.api.answer.NutzerGruppenBasicDataAPI;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.service.ApplicationService;
import io.swagger.v3.oas.models.links.Link;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
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

    if(!applicationService.exists(id)){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

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
//works
  @PostMapping("/gruppen/{id}/auslagen")
  public ResponseEntity<String> getTransaktionen(@PathVariable String id,
      @RequestBody AusgabenDataAPI neueAusgabe){
    if(!applicationService.exists(id)){
      return new ResponseEntity<>("Gruppe " + id + " nicht gefunden.", HttpStatus.NOT_FOUND);
    }
    if(applicationService.isClosed(id)){
      return new ResponseEntity<>("Gruppe " + id + " ist geschlossen.", HttpStatus.CONFLICT);
    }

    Set<String> bettler = new HashSet<>(neueAusgabe.schuldner());
    double betrag = (double)(neueAusgabe.cent()/100);
    applicationService.addTransaktionToGruppe(id, neueAusgabe.glaeubiger(), bettler, betrag);

    return new ResponseEntity<>("Ausgabe zu Gruppe " + id + " hinzugefuegt.", HttpStatus.CREATED);
  }

//works
  @GetMapping("/gruppen/{id}/ausgleich")
  public ResponseEntity<LinkedList<AusgleichDataAPI>> notwendigeTransaktionen(@PathVariable String id){
    if(!applicationService.exists(id)){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    LinkedList<AusgleichDataAPI> ausgleichsTransaktionen = new LinkedList<>();

    HashMap<String, HashMap<String, Double>> gruppenTransaktionen = applicationService.notwendigeTransaktionen(
        id);
    for (Entry<String, HashMap<String, Double>> nutzerTransaktionen : gruppenTransaktionen.entrySet()) {
      String von = nutzerTransaktionen.getKey();
      for (Entry<String, Double> transaktion : nutzerTransaktionen.getValue().entrySet()) {
        String an = transaktion.getKey();

        int cent = (int)(transaktion.getValue() * 100);
        AusgleichDataAPI ausgleich = new AusgleichDataAPI(von, an, cent);
        ausgleichsTransaktionen.add(ausgleich);
      }
    }

    return new ResponseEntity<>(ausgleichsTransaktionen, HttpStatus.OK);
  }
}

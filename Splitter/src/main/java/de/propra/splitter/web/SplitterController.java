package de.propra.splitter.web;

import de.propra.splitter.applicationservice.ApplicationService;
import de.propra.splitter.domain.model.Gruppe;
import de.propra.splitter.domain.service.GruppenService;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SplitterController {

  private final ApplicationService applicationService;
  @Autowired
  public SplitterController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }


  @GetMapping("/")
public String alleGruppen(OAuth2AuthenticationToken auth, Model m){

  String login = auth.getPrincipal().getAttribute("login");
  m.addAttribute("nutzername", login);

  HashSet<Integer> gruppen = applicationService.nutzerGruppen(login);
  m.addAttribute("gruppen", gruppen);

  return "alleGruppen";
  }

  @PostMapping("/alleGruppen")
  public String neueGruppe(Model m){

    String gruppenName = (String) m.getAttribute("neueGruppe");
    System.out.println(gruppenName);
    applicationService.addGruppe(gruppenName, "neonique");

    return "alleGruppen";
  }

  @GetMapping("/gruppe")
  public String gruppe(Model m){
    return "gruppe";
  }
  @GetMapping("/ausgleichsTransaktionen")
  public String ausgleichsTransaktionen(Model m){
    return "ausgleichsTransaktionen";
  }
  @GetMapping("/addTransaktion")
  public String addTransaktion(Model m){
    return "addTransaktion";
  }
}

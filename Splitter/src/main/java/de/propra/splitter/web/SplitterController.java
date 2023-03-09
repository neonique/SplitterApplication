package de.propra.splitter.web;

import de.propra.splitter.applicationservice.ApplicationService;
import de.propra.splitter.domain.model.Gruppe;
import de.propra.splitter.domain.model.Nutzer;
import de.propra.splitter.domain.service.GruppenService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SplitterController {

  private final ApplicationService applicationService;

  @Autowired
  public SplitterController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }


  @GetMapping("/")
  public String authentifizierung(OAuth2AuthenticationToken auth, RedirectAttributes attrs){

  String login = auth.getPrincipal().getAttribute("login");
  attrs.addFlashAttribute("nutzername", login);


    return "redirect:/alleGruppen";
  }

  @GetMapping("/alleGruppen")
  public String alleGruppen(Model m){

    return "alleGruppen";
  }
  @PostMapping("/alleGruppen")
  public String neueGruppe( RedirectAttributes attrs,String nutzername){
    attrs.addFlashAttribute("nutzername", nutzername);
    return "redirect:/alleGruppen";
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

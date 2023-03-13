package de.propra.splitter.web;

import de.propra.splitter.service.ApplicationService;

import java.util.HashMap;
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
  public String alleGruppen(Model m,OAuth2AuthenticationToken auth){
    String login = auth.getPrincipal().getAttribute("login");
    m.addAttribute("nutzername",login);
    HashMap<String, String> offeneNutzerGruppen = applicationService.offeneNutzerGruppen(login);
    m.addAttribute("offeneNutzerGruppen", offeneNutzerGruppen );
    HashMap<String, String> geschlosseneNutzerGruppen = applicationService.geschlosseneNutzerGruppen(login);
    m.addAttribute("geschlosseneNutzerGruppen", geschlosseneNutzerGruppen );
    return "alleGruppen";
  }
  @PostMapping("/alleGruppen")
  public String gruppeErstellen( RedirectAttributes attrs,OAuth2AuthenticationToken auth, String neueGruppe){
    String login = auth.getPrincipal().getAttribute("login");
    applicationService.addGruppe(neueGruppe, login);
    return "redirect:/alleGruppen";
  }

  @GetMapping("/gruppe")
  public String gruppe(Model m,OAuth2AuthenticationToken auth){
    String gruppenid = (String) m.getAttribute("gruppenid");
    boolean geschlossen=applicationService.isClosed(gruppenid);
    String gruppenName =applicationService.getName(gruppenid);
    m.addAttribute("gruppenid", gruppenid);
    m.addAttribute("gruppenName",gruppenName);
    m.addAttribute("geschlossen",geschlossen);

    System.out.println(applicationService.getGruppenNutzer(gruppenid));
    return "gruppe";
  }

  @GetMapping("/gruppe/auswahl")
  public String waehleGruppe(OAuth2AuthenticationToken auth,String gruppenid,RedirectAttributes attrs){
    attrs.addFlashAttribute("gruppenid",gruppenid);

    return "redirect:/gruppe";
  }
  @PostMapping("/gruppe/close")
  public String closeGruppe(OAuth2AuthenticationToken auth,String gruppenid,RedirectAttributes attrs){
    applicationService.closeGruppe(gruppenid);
    attrs.addFlashAttribute("gruppenid",gruppenid);
    return "redirect:/gruppe";
  }

  @PostMapping("/gruppe/neuerNutzer")
  public String neuerNutzer(OAuth2AuthenticationToken auth,String gruppenid,String neuerNutzer,RedirectAttributes attrs){
    applicationService.addNutzerToGruppe(gruppenid,neuerNutzer);
    attrs.addAttribute("gruppenid",gruppenid);
    return "redirect:/gruppe";
  }
  @GetMapping("/ausgleichsTransaktionen")
  public String ausgleichsTransaktionen(Model m,OAuth2AuthenticationToken auth){
    return "ausgleichsTransaktionen";
  }
  @GetMapping("/addTransaktion")
  public String addTransaktion(Model m,OAuth2AuthenticationToken auth){
    return "addTransaktion";
  }
}

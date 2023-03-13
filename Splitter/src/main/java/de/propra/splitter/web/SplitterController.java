package de.propra.splitter.web;

import de.propra.splitter.domain.Transaktion;
import de.propra.splitter.domain.TransaktionDTO;
import de.propra.splitter.service.ApplicationService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
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
    //m.addAttribute("gruppenid", gruppenid);
    m.addAttribute("gruppenName",gruppenName);
    m.addAttribute("geschlossen",geschlossen);
    Set<String> gruppeNutzer = applicationService.getGruppenNutzer(gruppenid);
    Set<TransaktionDTO> gruppeTransaktionen = applicationService.getGruppenTransaktionen(gruppenid);
    boolean hasTransaktionen = !gruppeTransaktionen.isEmpty();
    m.addAttribute("gruppeNutzer", gruppeNutzer);
    m.addAttribute("hasTransaktionen", hasTransaktionen);
    m.addAttribute("gruppeTransaktionen",gruppeTransaktionen);


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
    attrs.addFlashAttribute("gruppenid",gruppenid);
    return "redirect:/gruppe";
  }
  @GetMapping("/ausgleichsTransaktionen")
  public String ausgleichsTransaktionen(Model m,OAuth2AuthenticationToken auth){
    String gruppenid = (String) m.getAttribute("gruppenid");
    String gruppeName = applicationService.getName(gruppenid);
    HashMap<String, HashMap<String, Double>> notwendigeTransaktionen = applicationService.notwendigeTransaktionen(gruppenid);
    m.addAttribute("notwendigeTransaktionen",notwendigeTransaktionen);
    m.addAttribute("gruppenName",gruppeName);
    return "ausgleichsTransaktionen";
  }
  @GetMapping("/gruppe/addTransaktion")
  public String addTransaktionZuGruppe(OAuth2AuthenticationToken auth,String gruppenid,RedirectAttributes attrs){
    attrs.addFlashAttribute("gruppenid", gruppenid);
    return "redirect:/neueTransaktion";
  }
  @GetMapping("/neueTransaktion")
  public String addTransaktion(Model m,OAuth2AuthenticationToken auth){
    String gruppenid = (String) m.getAttribute("gruppenid");
    Set<String> gruppeNutzer = applicationService.getGruppenNutzer(gruppenid);
    m.addAttribute("gruppeNutzer", gruppeNutzer);

    return "addTransaktion";
  }
  @PostMapping("/neueTransaktion")
  public String addTransaktionPost(Model m,OAuth2AuthenticationToken auth,String sponsor, HttpServletRequest request, Double betrag, String gruppenid, RedirectAttributes attrs){
    attrs.addFlashAttribute("gruppenid", gruppenid);
    Set<String> gruppeNutzer = applicationService.getGruppenNutzer(gruppenid);
    Set<String> beggarSet = new HashSet<>();
    int size = gruppeNutzer.size();
    for (String s : gruppeNutzer) {
      String name = request.getParameter("checkbox"+ s);
      if(name != null) {
        beggarSet.add(name);
      }
    }
   applicationService.addTransaktionToGruppe(gruppenid, sponsor, beggarSet, betrag);

    return "redirect:/gruppe";
  }
  @PostMapping("/gruppe/notwendigeTransaktionen")
  public String notwendigTransaktion(OAuth2AuthenticationToken auth,String gruppenid,RedirectAttributes attrs){
    attrs.addFlashAttribute("gruppenid",gruppenid);
    return "redirect:/ausgleichsTransaktionen";
  }

}

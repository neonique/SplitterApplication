package de.propra.splitter.web;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SplitterController {



  public SplitterController(){
    System.out.println("abc");
  }
  @GetMapping("/")
  public String startSeite(OAuth2AuthenticationToken auth, Model m){

    return "anmelden";
  }
  @PostMapping("/")
  public String post(){

    return "anmelden";

}

@GetMapping("/alleGruppen")
public String alleGruppen(Model m){
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

package de.propra.splitter.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SpliterController {

  @GetMapping("/")
  public String startSeite(){
    return "startSeite" ;
  }

}

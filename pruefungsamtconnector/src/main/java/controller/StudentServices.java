package controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentServices {

    @RequestMapping("/check")
    public String greeting() {
        return "ok";
    }
    
    @RequestMapping("/credits/{matrikelnr}")
    public int getCredits(@PathVariable int matrikelnr) {
      return matrikelnr;
      
    }
}
package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckService {

    @RequestMapping("/check")
    public String greeting() {
        return "ok";
    }
}
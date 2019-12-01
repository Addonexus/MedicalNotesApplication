package nsa.group4.medical.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("404")
    public String errorPage() {
        return "404";
    }
}

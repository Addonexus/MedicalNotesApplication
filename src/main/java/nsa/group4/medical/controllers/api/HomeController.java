package nsa.group4.medical.controllers.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }
}

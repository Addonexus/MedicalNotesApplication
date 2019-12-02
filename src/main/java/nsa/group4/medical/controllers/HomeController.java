package nsa.group4.medical.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homepage() {
        return "index";
    }

    @GetMapping("/404")
    public String missingPage() {
        return "404";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

    @GetMapping("/calendar")
    public String calendarPage() {
        return "calender";
    }
}


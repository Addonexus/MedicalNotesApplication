package nsa.group4.medical.controllers;

import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.service.implementations.CategoryServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private CategoryServiceInterface categoryService;
    private CaseServiceInterface caseService;

    public HomeController(CaseServiceInterface caseService,
                          CategoryServiceInterface categoryService
                          ){
        this.caseService = caseService;
        this.categoryService = categoryService;

    }

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

//  TODO: move this to a separate controller
    @GetMapping("/calendar")
    public String calendarPage() {
        return "calendar";
    }

    @GetMapping(path = "/home")
    public String allCases(Model model) {
        List<Categories> categories = categoryService.findAll();
        List<CaseModel> cases = caseService.findAll();
        System.out.println("CATEGORIES TSET: "+categories.toString());
        System.out.println("CASES TSET: "+cases.toString());
//        model.addAttribute("cases", cases);
        model.addAttribute("categoryKey", new Categories());
        model.addAttribute("categories", categories);
        return "main/home";
//        }
    }


}


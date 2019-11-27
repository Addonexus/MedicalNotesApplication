package nsa.group4.medical.controllers;

import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.service.DiagnosisServiceInterface;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    private CaseServiceInterface caseService;

    private DiagnosisServiceInterface diagnosisService;//replace with the service class for diagnosis when implemented

    private CategoriesRepositoryJPA categoriesRepositoryJPA;

    public SearchController(CaseServiceInterface caseService, DiagnosisServiceInterface diagnosisService,
                            CategoriesRepositoryJPA categoriesRepositoryJPA){
        this.caseService = caseService;
        this.diagnosisService = diagnosisService;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "search") String q, Model model) {
        List<Diagnosis> diagnoses = diagnosisService.findAll();
        List<Diagnosis> diagnosisContainingQuery = new ArrayList<>();
        for (Diagnosis d : diagnoses) {
            if (d.searchable().contains(q)) {
                diagnosisContainingQuery.add(d);
            }
        }

        List<CaseModel> cases = caseService.findAll();
        List<CaseModel> casesContainingQuery = new ArrayList<>();
        for (CaseModel c : cases) {
            if (c.getName().contains(q)) {
                casesContainingQuery.add(c);
            }
        }

        List<Categories> categoriesList = categoriesRepositoryJPA.findAll();
        List<Categories> categoriesContainingQuery = new ArrayList<>();
        for (Categories cat : categoriesList) {
            if (cat.getName().contains(q)) {
                categoriesContainingQuery.add(cat);
            }
        }

        model.addAttribute("diagnoses", diagnosisContainingQuery);
        model.addAttribute("cases", casesContainingQuery);
        model.addAttribute("categories", categoriesContainingQuery);
        model.addAttribute("query", q);
        System.out.println(q);
        return "searchPage";

    }

    @GetMapping("/searchTemp")
    public String searchTemp() {
        return "searchTemp";
    }

}

package nsa.group4.medical.controllers;

import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@Controller
public class DiagnosesController {

    private DiagnosisRepositoryJPA diagnosisRepositoryJPA;
    private CategoriesRepositoryJPA categoriesRepositoryJPA;

    @Autowired
    public DiagnosesController(DiagnosisRepositoryJPA diagnosisRepositoryJPA,
                               CategoriesRepositoryJPA categoriesRepositoryJPA) {
        this.diagnosisRepositoryJPA = diagnosisRepositoryJPA;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
    }

    @GetMapping("/diagnoses/{index}")
    public String getDiagnoses(Model model, @PathVariable final Long index) {
        Optional<Categories> categoriesOptional = categoriesRepositoryJPA.findById(index);
        List<Diagnosis> diagnoses = diagnosisRepositoryJPA.findByCategories(categoriesOptional.get());
        model.addAttribute("diagnoses", diagnoses);
        return "home";
    }
}

package nsa.group4.medical.controllers;

import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.web.CaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @GetMapping("dia/{index}")
    public String getDiagnoses(Model model,
                               @PathVariable final Long index) {
        Optional<Categories> categoriesOptional = categoriesRepositoryJPA.findById(index);

        List<Diagnosis> diagnoses = diagnosisRepositoryJPA.findByCategories(categoriesOptional.get());
        model.addAttribute("diagnosisKey", new Diagnosis());
        model.addAttribute("diagnoses", diagnoses);
        return "home";
    }

    @PostMapping("dia/{index}")
    public String newDiagnosis(@PathVariable final Long index,
                               @ModelAttribute("diagnosisKey") Diagnosis diagnosis,
                               Model model) {
        Categories categories = categoriesRepositoryJPA.findById(index).get();
        diagnosis.setCategories(categories);
        diagnosisRepositoryJPA.save(diagnosis);
        List<Diagnosis> diagnoses = diagnosisRepositoryJPA.findByCategories(categories);
        model.addAttribute("diagnosisKey", new Diagnosis());
        model.addAttribute("diagnoses", diagnoses);
        return "home";

    }
}
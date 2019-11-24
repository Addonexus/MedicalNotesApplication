package nsa.group4.medical.controllers;

import com.sun.istack.Nullable;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.service.DiagnosisRepositoryInterface;
import nsa.group4.medical.web.CaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class DiagnosesController {

    private DiagnosisRepositoryInterface diagnosisRepository;
    private CategoriesRepositoryJPA categoriesRepositoryJPA;

    @Autowired
    public DiagnosesController(DiagnosisRepositoryInterface diagnosisRepository,
                               CategoriesRepositoryJPA categoriesRepositoryJPA) {
        this.diagnosisRepository = diagnosisRepository;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
    }


//    @GetMapping("dia/{index}")
//    public String getDiagnoses(Model model,
//                               @PathVariable final Long index) {
//        Optional<Categories> categoriesOptional = categoriesRepositoryJPA.findById(index);
//
//        List<Diagnosis> diagnoses = diagnosisRepository.findByCategories(categoriesOptional.get());
//        model.addAttribute("diagnosisKey", new Diagnosis());
//        model.addAttribute("diagnoses", diagnoses);
//        return "home";
//    }



    @PostMapping("dia/{index}")
    public String newDiagnosis(@PathVariable final Long index,
                               @ModelAttribute("diagnosisKey") Diagnosis diagnosis,
                               Model model) {
        Categories categories = categoriesRepositoryJPA.findById(index).get();
        diagnosis.setCategories(categories);

        diagnosisRepository.save(diagnosis);
        List<Diagnosis> diagnoses = diagnosisRepository.findByCategories(categories);

        if(diagnoses.isEmpty()){
            return "404";
        }
        else{
            model.addAttribute("diagnosisKey", new Diagnosis());
            model.addAttribute("diagnoses", diagnoses);
            return "home";
        }
    }

    @GetMapping("ya")
    public String ya() {
        return "testAutocompleteChips";
    }
}
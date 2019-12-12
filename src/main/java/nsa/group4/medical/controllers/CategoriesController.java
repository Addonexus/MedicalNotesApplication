package nsa.group4.medical.controllers;

import nsa.group4.medical.Helper.Helpers;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.service.implementations.CategoryServiceInterface;
import nsa.group4.medical.service.implementations.DiagnosisServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CategoriesController {

    @Autowired
    private Helpers helpers;

    private CaseServiceInterface caseService;

    private DiagnosisServiceInterface diagnosisService;//replace with the service class for diagnosis when implemented

    private CategoryServiceInterface categoriesService;

    public CategoriesController(CaseServiceInterface caseService,
                                DiagnosisServiceInterface diagnosisService,
                                CategoryServiceInterface categoriesService){
        this.caseService = caseService;
        this.diagnosisService = diagnosisService;
        this.categoriesService = categoriesService;

    }

    static final Logger LOG =
            LoggerFactory.getLogger(CategoriesController.class);

    @GetMapping("/category/{index}")
    public String getCategories(Model model, @PathVariable final Long index) {
        List<Categories> categories = categoriesService.findAll();
        List<CaseModel> cases = caseService.findAll();

        Optional<Categories> thisCategory = categoriesService.findById(index);
        if(thisCategory.isPresent()){
            if(thisCategory.get().getUser().getId().equals(helpers.getUserId().getId())) {
//                List<Diagnosis> diagnoses = diagnosisService.findByCategories(thisCategory.get());
                Diagnosis diagnosisArg = new Diagnosis();
                diagnosisArg.setCategories(thisCategory.get());
                model.addAttribute("category", thisCategory.get());
                return "main/category";
            }
            return "403";
        }

        return "404";
    }

}

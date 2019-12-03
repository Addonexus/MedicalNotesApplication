package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.controllers.api.Form;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.DiagnosisInformation;
import nsa.group4.medical.service.DiagnosisRepositoryInterface;
import nsa.group4.medical.service.events.DiagnosisInformationAdded;
import nsa.group4.medical.web.CaseForm;
import nsa.group4.medical.web.DiagnosisInformationForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class DiagnosesController {

    private DiagnosisRepositoryInterface diagnosisRepository;
    private CategoriesRepositoryJPA categoriesRepositoryJPA;
    private DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;

    static final Logger LOG = LoggerFactory.getLogger(DiagnosesController.class);

    @Autowired
    public DiagnosesController(DiagnosisRepositoryInterface diagnosisRepository,
                               CategoriesRepositoryJPA categoriesRepositoryJPA,
                               DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC) {
        this.diagnosisRepository = diagnosisRepository;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;
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

    @PostMapping(value ="/diagnosis/{diagnosisIndex}/addDiagnosisInfo")
    public String saveDiagnosisInformation(@PathVariable(name="diagnosisIndex") Long diagnosisID,
                                           @ModelAttribute("diagnosisInfoKey") @Valid DiagnosisInformationForm diagnosisInformationForm,
                                           BindingResult bindingResult,
                                           Model model) {

        if (bindingResult.hasErrors()){
            LOG.debug(bindingResult.toString());
            return "diagnosisInformation";
        }

        LOG.debug("Wagawan: "+model.toString());
        LOG.debug("ChefBoyardee: "+diagnosisInformationForm.toString());

        DiagnosisInformationAdded diagnosisInformationAdded = new DiagnosisInformationAdded(
                diagnosisID,
                diagnosisInformationForm.getKey(),
                diagnosisInformationForm.getValue()
        );

        diagnosisInformationRepositoryJDBC.saveDiagnosisInformation(diagnosisInformationAdded);

        return "redirect:/diagnosis/{diagnosisIndex}";
    }


    @GetMapping(value ="/diagnosis/{diagnosisIndex}/addDiagnosisInfo")
    public String addDiagnosisInformation(@PathVariable(name="categoryIndex") Long categoryID,
                                          @PathVariable(name="diagnosisIndex") Long diagnosisID,
                                          Model model){

        Optional<Categories> category = categoriesRepositoryJPA.findById(categoryID);

        if (!category.isPresent()){
            return "404";
        }

        model.addAttribute("diagnosisInfoKey", new DiagnosisInformationForm());

        return "diagnosisInformation";
    }


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

    @GetMapping("/returnedDiagnosisInfo")
    public @ResponseBody List<DiagnosisInformation> getDiagnosisInformation() {
        return diagnosisInformationRepositoryJDBC.getAllDiagnosisInformation();
    }

    @GetMapping("ya")
    public String ya(Model model) {


        model.addAttribute("form", new Form());
        return "testAutocompleteChips";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
}
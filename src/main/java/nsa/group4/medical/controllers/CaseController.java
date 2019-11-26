package nsa.group4.medical.controllers;


//import jdk.internal.jline.internal.Log;
//import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.DiagnosisInformation;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.service.DiagnosisServiceInterface;
import nsa.group4.medical.web.CaseForm;
//import nsa.group4.medical.web.CaseTestForm;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//import static com.sun.tools.doclint.Entity.or;
//@SessionAttributes("categoryKey")
@Controller
@Slf4j
//@SessionAttributes("category")
public class CaseController {

    private CaseServiceInterface caseService;

    private DiagnosisServiceInterface diagnosisService;//replace with the service class for diagnosis when implemented

    private CategoriesRepositoryJPA categoriesRepositoryJPA;

    private DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;

    public CaseController(CaseServiceInterface caseService, DiagnosisServiceInterface diagnosisService,
                          CategoriesRepositoryJPA categoriesRepositoryJPA,
                          DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC){
        this.caseService = caseService;
        this.diagnosisService = diagnosisService;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;

    }

    static final Logger LOG = LoggerFactory.getLogger(CaseController.class);

    @RequestMapping(path="/category/{categoryId}/createNewCase",
            method = RequestMethod.GET)
    public String createNewCase(@PathVariable(name="categoryId") Long categoryId,
                                Model model){
//        List<CaseModel> returnedCases = caseService.findCasesByDiagnosisId(diagnosisId);
        Optional<Categories> category = categoriesRepositoryJPA.findById(categoryId);
//        Optional<Diagnosis> diagnosis = diagnosisService.getByDiagnosisId(diagnosisId);
        if(!category.isPresent() ){
            return "404";
        }
        CaseForm caseForm = new CaseForm();
//        caseForm.setCategory(category.get());
//        caseForm.set
//        log.debug("Category before case shown: " +caseForm.getCategory());
        log.debug("CASE BEFORE SHWON: "+ caseForm);
//        log.debug("CATEGORY BEFORE SHOWN: " + category.get());
        model.addAttribute("caseKey", caseForm);
        model.addAttribute("categoryIndex", category.get().getId());
//        model.addAttribute("categoryKey", category.get());

        return "newCase";
    }

    @RequestMapping(path="/caseDetails/{categoryIndex}", method = RequestMethod.POST)
    public String caseAdded(@PathVariable("categoryIndex") Long categoryId,
                            @ModelAttribute("caseKey") @Valid  CaseForm caseForm,
                            BindingResult bindingResult,
                            Model model){


//        log.debug(categories.toString());


        if (bindingResult.hasErrors()){
            log.debug("BINDING ERROR" +bindingResult.toString());
            log.debug("FIELDS HAVE BINDING ERRORS");
            return "newCase";
        }
        log.debug(caseForm.toString());

        String diagnoses = caseForm.getDiagnosesList();
//        log.debug("Testing category from FORM:" + caseForm.getCategory());
//        log.debug("Testing category id:" + categoryId);
        Optional<Categories> categories = categoriesRepositoryJPA.findById(categoryId);

        //      splitting the diagnosis text box by a "," delimiter and then trimmed trailing and leading whitespaces
        List<String> diagnosesList = Arrays.stream(diagnoses.split(",")).map(String::trim).collect(Collectors.toList());

        //      finding all of the existing diagnosis records
        List<Diagnosis> existingDiagnosis = diagnosisService.findByCaseNameIn(diagnosesList);
//        String strings = existingDiagnosis.stream().map(x -> x.getName()).collect(Collectors.joining(","));

        //      filters the list of existing diagnosis and returns all of the new diagnosis objects that have to be made
        List<String> notExistingDiagnosis = diagnosesList.stream().filter(x -> existingDiagnosis.stream().noneMatch(
                diagnosis -> diagnosis.getName().equals(x))).collect(Collectors.toList());
//        System.out.println("GTFIO:" + notExistingDiagnosis);

        //      creates new Diagnosis Objects with each item in the list
        List<Diagnosis> storingDiagnosis = notExistingDiagnosis.stream().map(x -> new Diagnosis(x,categories.get())).collect(Collectors.toList());
        CaseModel caseModel = new CaseModel(caseForm.getName(), caseForm.getDemographics());

//      storing both diagnosis list objects into the case diagnosis list
        caseModel.getDiagnosesList().addAll(storingDiagnosis);
        caseModel.getDiagnosesList().addAll(existingDiagnosis);
        caseService.createCase(caseModel);

//        return "newCase";//redirect to the case page that has just been created
//        model.addAttribute("attribute", "redirectWithRedirectPrefix");
//        session.invalidate();
        String url = "redirect:/category/"+categoryId;
        return url;
    }

    @GetMapping(path ="/case/{index}")
    public String getCase(@PathVariable(name="index") Long index, Model model){
        Optional<CaseModel> returnedCase = caseService.findByCaseId(index);
        if(returnedCase.isPresent()){
            model.addAttribute("case", returnedCase.get());
            return "case";
        }
        return "404";
    }

    @GetMapping(path ="/category/{categoryIndex}/diagnosis/{diagnosisIndex}")
    public String getCases(@PathVariable(name="categoryIndex") Long categoryId,
                           @PathVariable(name="diagnosisIndex") Long diagnosisId,
                           Model model){

        List<CaseModel> returnedCases = caseService.findCasesByDiagnosisId(diagnosisId);
        List<CaseModel> recentCases = caseService.findAll();
        Optional<Categories> category = categoriesRepositoryJPA.findById(categoryId);
        log.debug("CASES: " + returnedCases);

        log.debug("CASES 2: " + recentCases);
        log.debug("CAT: " + category);

        if(!category.isPresent()){
            return "404";
        }

        List<DiagnosisInformation> diagnosisInformations = diagnosisInformationRepositoryJDBC.getDiagnosisInformationByDiagnosisId(diagnosisId);

        model.addAttribute("cases", recentCases);
        model.addAttribute("returnedCases", returnedCases);
        model.addAttribute("category", category.get());
        model.addAttribute("returnedDiagnosisInfo", diagnosisInformations);

        return "home";
    }

    @GetMapping(path = "/home")
    public String allCases(Model model) {
        List<Categories> categories = categoriesRepositoryJPA.findAll();
        List<CaseModel> cases = caseService.findAll();
        if(cases.isEmpty()){
            return "404";
        }
        else{
            model.addAttribute("cases", cases);
            model.addAttribute("categoryKey", new Categories());
            model.addAttribute("categories", categories);
            return "home";
        }
    }

    @PostMapping(path = "/home")
    public String allCasesPost(Model model, @ModelAttribute("categoryKey") Categories categoryKey) {

        categoriesRepositoryJPA.save(categoryKey);

        List<Categories> categories = categoriesRepositoryJPA.findAll();
        List<CaseModel> cases = caseService.findAll();
        if(cases.isEmpty()){
            return "404";
        }
        else{
            model.addAttribute("cases", cases);
            model.addAttribute("categories", categories);
            return "home";
        }
    }
}

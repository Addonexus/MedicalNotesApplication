package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.controllers.api.Form;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.data.NotificationRepoJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.service.DiagnosisRepositoryInterface;
import nsa.group4.medical.service.DiagnosisServiceInterface;
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


    private CaseServiceInterface caseService;
    private DiagnosisServiceInterface diagnosisService;
    private NotificationRepoJPA notificationRepoJPA;
    private DiagnosisRepositoryInterface diagnosisRepository;
    private CategoriesRepositoryJPA categoriesRepositoryJPA;
    private DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;

    static final Logger LOG = LoggerFactory.getLogger(DiagnosesController.class);

    @Autowired
    public DiagnosesController(CaseServiceInterface caseService,
                               DiagnosisServiceInterface diagnosisService,
                               NotificationRepoJPA notificationRepoJPA,
                               DiagnosisRepositoryInterface diagnosisRepository,
                               CategoriesRepositoryJPA categoriesRepositoryJPA,
                               DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC) {
        this.diagnosisRepository = diagnosisRepository;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;
        this.caseService = caseService;
        this.diagnosisService = diagnosisService;
        this.notificationRepoJPA = notificationRepoJPA;
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
                null,
                diagnosisID,
                diagnosisInformationForm.getKey(),
                diagnosisInformationForm.getValue()
        );

        diagnosisInformationRepositoryJDBC.saveDiagnosisInformation(diagnosisInformationAdded);

        return "redirect:/diagnosis/{diagnosisIndex}";
    }


    //Method for retrieving page to add diagnosis info.
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


    @GetMapping(path ="diagnosis/{diagnosisIndex}")
    public String getCases(@PathVariable(name="diagnosisIndex") Long diagnosisId,
                           Model model){

        List<CaseModel> returnedCases = caseService.findCasesByDiagnosisId(diagnosisId);
        List<CaseModel> recentCases = caseService.findAll();
        log.debug("CASES: " + returnedCases);
        log.debug("CASES 2: " + recentCases);

        //TODO: move notification business logic into the notification service class
        Notifications n = notificationRepoJPA.findByDiagnosisLink(diagnosisService.findById(diagnosisId).get());
        if(n!=null){
            System.out.println("notification: " + n);
            n.setRead(true);
            notificationRepoJPA.save(n);
        }

        List<DiagnosisInformation> diagnosisInformations = diagnosisInformationRepositoryJDBC.getDiagnosisInformationByDiagnosisId(diagnosisId);
        model.addAttribute("diagnosisInfoKey", new DiagnosisInformationForm());
        model.addAttribute("cases", recentCases);
        model.addAttribute("returnedCases", returnedCases);
        model.addAttribute("category", diagnosisService.findById(diagnosisId).get().getCategories());
        model.addAttribute("returnedDiagnosisInfo", diagnosisInformations);
        model.addAttribute("diagnosisName", diagnosisService.findById(diagnosisId).get().getName());

        return "home";
    }


    @GetMapping("ya")
    public String ya(Model model) {


        model.addAttribute("form", new Form());
        return "testAutocompleteChips";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }
}
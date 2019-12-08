package nsa.group4.medical.controllers;


//import jdk.internal.jline.internal.Log;
//import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.controllers.api.AjaxResponseBody;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.data.NotificationRepoJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.service.DiagnosisServiceInterface;
import nsa.group4.medical.web.CaseForm;
//import nsa.group4.medical.web.CaseTestForm;
import nsa.group4.medical.web.DiagnosisInformationForm;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.management.Notification;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
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

    private NotificationRepoJPA notificationRepoJPA;

    public CaseController(CaseServiceInterface caseService, DiagnosisServiceInterface diagnosisService,
                          CategoriesRepositoryJPA categoriesRepositoryJPA,
                          DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC,
                          NotificationRepoJPA notificationRepoJPA){
        this.caseService = caseService;
        this.diagnosisService = diagnosisService;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;
        this.notificationRepoJPA = notificationRepoJPA;

    }

    static final Logger LOG = LoggerFactory.getLogger(CaseController.class);

    @RequestMapping(path="/createNewCase",
            method = RequestMethod.GET)
    public String createNewCase(Model model){
        CaseForm caseForm = new CaseForm();
        log.debug("CASE BEFORE SHWON: "+ caseForm);
        model.addAttribute("caseKey", caseForm);

        return "newCase";
    }


    @GetMapping(path ="/case/{index}")
    public String getCase(@PathVariable(name="index") Long index, Model model){
        Optional<CaseModel> returnedCase = caseService.findByCaseId(index);
        if(returnedCase.isPresent()){
            model.addAttribute("case", returnedCase.get());
            model.addAttribute("caseKey", new CaseForm());
            model.addAttribute("hiddenForm", "1");
            return "newCase";
        }
        return "404";
    }

    @GetMapping(path = "/home")
    public String allCases(Model model) {
        List<Categories> categories = categoriesRepositoryJPA.findAll();
        List<CaseModel> cases = caseService.findAll();
        System.out.println("CATEGORIES TSET: "+categories.toString());
        System.out.println("CASES TSET: "+cases.toString());
        model.addAttribute("cases", cases);
        model.addAttribute("categoryKey", new Categories());
        model.addAttribute("categories", categories);
        return "home";
//        }
    }

}

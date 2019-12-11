package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.FreehandNotesRepoJDBC;
import nsa.group4.medical.data.NotificationRepoJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.implementations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class DiagnosesController {

//  TODO:Remove any direct repository calls and move them to the service layer
    private CaseServiceInterface caseService;
    private DiagnosisServiceInterface diagnosisService;
    private NotificationServiceInterface notificationService;
    private DiagnosisRepositoryInterface diagnosisRepository;
    private CategoryServiceInterface categoryService;
    private DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;
    private FreehandNotesRepoJDBC freehandNotesRepoJDBC;

    static final Logger LOG = LoggerFactory.getLogger(DiagnosesController.class);

    @Autowired
    public DiagnosesController(CaseServiceInterface caseService,
                               DiagnosisServiceInterface diagnosisService,
                               NotificationServiceInterface notificationService,
                               DiagnosisRepositoryInterface diagnosisRepository,
                               CategoryServiceInterface categoryService,
                               DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC,
                               FreehandNotesRepoJDBC freehandNotesRepoJDBC) {
        this.diagnosisRepository = diagnosisRepository;
        this.categoryService = categoryService;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;
        this.caseService = caseService;
        this.diagnosisService = diagnosisService;
        this.notificationService = notificationService;
        this.freehandNotesRepoJDBC = freehandNotesRepoJDBC;
    }

    @GetMapping(path ="/diagnosis/{diagnosisIndex}")
    public String getCases(@PathVariable(name="diagnosisIndex") Long diagnosisId,
                           Model model){

        List<CaseModel> returnedCases = caseService.findCasesByDiagnosisId(diagnosisId);
        List<CaseModel> recentCases = caseService.findAll();
        log.debug("CASES: " + returnedCases);
        log.debug("CASES 2: " + recentCases);

        //TODO: move notification business logic into the notification service class
        Notifications n = notificationService.findByDiagnosisLink(diagnosisService.findById(diagnosisId).get());
        if(n!=null){
            System.out.println("notification: " + n);
            n.setRead(true);
            notificationService.saveNotification(n);
        }

//        List<DiagnosisInformation> diagnosisInformations = diagnosisInformationRepositoryJDBC.getDiagnosisInformationByDiagnosisId(diagnosisId);
        model.addAttribute("returnedCases", returnedCases);
        model.addAttribute("category", diagnosisService.findById(diagnosisId).get().getCategories());
        model.addAttribute("diagnosisName", diagnosisService.findById(diagnosisId).get().getName());
        return "main/diagnosis";
    }
//  TODO: move this to separate controller
//    @GetMapping("/login")
//    public String getLoginPage(){
//        return "login";
//    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
}
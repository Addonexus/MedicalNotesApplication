package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.DiagnosisService;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.web.CaseForm;
import nsa.group4.medical.web.DiagnosisInformationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@SessionAttributes("categoryKey")
@Controller
@Slf4j
//TODO: add javadocs
//@SessionAttributes("category")
public class CaseController {

    @Autowired
    DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;

    @Autowired
    DiagnosisService diagnosisService;

    @Autowired
    CategoriesRepositoryJPA categoriesRepositoryJPA;

    private CaseServiceInterface caseService;
    public CaseController(CaseServiceInterface caseService, DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC, DiagnosisService diagnosisService, CategoriesRepositoryJPA categoriesRepositoryJPA){
        this.caseService = caseService;
        this.diagnosisInformationRepositoryJDBC = diagnosisInformationRepositoryJDBC;
        this.diagnosisService = diagnosisService;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
    }

    @RequestMapping(path="/createNewCase", method = RequestMethod.GET)
    public String createNewCase(Model model){
        log.debug("----GET Mapping: /createNewCase----");
        CaseForm caseForm = new CaseForm();
        log.debug("CASE FORM: "+ caseForm);
        model.addAttribute("caseKey", caseForm);
        return "main/case";
    }

    @GetMapping(path ="/case/{index}")
    public String getCase(@PathVariable(name="index") Long index, Model model){
        log.debug("----GET Mapping: /case/{index}----");
        Optional<CaseModel> returnedCase = caseService.findByCaseId(index);
        log.debug("RETURNED CASE: "+ returnedCase);
        if(returnedCase.isPresent()){
            model.addAttribute("case", returnedCase.get());
            model.addAttribute("caseKey", new CaseForm());
            model.addAttribute("hiddenForm", "1");
            return "main/case";
        }
        return "404";
    }

    @GetMapping(path ="diagnosis/{diagnosisIndex}")
    public String getCases(@PathVariable(name="diagnosisIndex") Long diagnosisId,
                           Model model){

        List<CaseModel> returnedCases = caseService.findCasesByDiagnosisId(diagnosisId);
        List<CaseModel> recentCases = caseService.findAll();
//        Optional<Categories> category = categoriesRepositoryJPA.findById(categoryId);
        log.debug("CASES: " + returnedCases);

        log.debug("CASES 2: " + recentCases);
//        log.debug("CAT: " + category);

//        if(!category.isPresent()){
//            return "404";
//        }

        List<DiagnosisInformation> diagnosisInformations = diagnosisInformationRepositoryJDBC.getDiagnosisInformationByDiagnosisId(diagnosisId);

        model.addAttribute("diagnosisInfoKey", new DiagnosisInformationForm());
        model.addAttribute("cases", recentCases);
        model.addAttribute("returnedCases", returnedCases);
        model.addAttribute("category", diagnosisService.findById(diagnosisId).get().getCategories());
//        model.addAttribute("category", category.g());
        model.addAttribute("returnedDiagnosisInfo", diagnosisInformations);
        model.addAttribute("diagnosisName", diagnosisService.findById(diagnosisId).get().getName());

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

    @GetMapping("/recentCases")
    public @ResponseBody
    List<CaseModel> getRecentCases() {
        return caseService.findAllByOrderByCreationDate();
    }
}

package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.web.CaseForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

//@SessionAttributes("categoryKey")
@Controller
@Slf4j
//TODO: add javadocs
//@SessionAttributes("category")
public class CaseController {

    private CaseServiceInterface caseService;
    public CaseController(CaseServiceInterface caseService){
        this.caseService = caseService;
    }

    @RequestMapping(path="/createNewCase", method = RequestMethod.GET)
    public String createNewCase(Model model){
        log.debug("----GET Mapping: /createNewCase----");
        CaseForm caseForm = new CaseForm();
        log.debug("CASE FORM: "+ caseForm);
        model.addAttribute("caseKey", caseForm);
        return "newCase";
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
            return "newCase";
        }
        return "404";
    }
}
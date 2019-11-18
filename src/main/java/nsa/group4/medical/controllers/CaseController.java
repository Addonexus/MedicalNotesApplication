package nsa.group4.medical.controllers;


import nsa.group4.medical.domains.CaseModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CaseController {


    @RequestMapping(path="/createNewCase", method = RequestMethod.GET)
    public String createNewCase(Model model){

        model.addAttribute("caseKey", new CaseModel());
        return "newCase";
    }
}

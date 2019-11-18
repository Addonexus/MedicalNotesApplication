package nsa.group4.medical.controllers;


import nsa.group4.medical.domains.CaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class CaseController {

    static final Logger LOG = LoggerFactory.getLogger(CaseController.class);

    @RequestMapping(path="/createNewCase", method = RequestMethod.GET)
    public String createNewCase(Model model){

        model.addAttribute("caseKey", new CaseModel());
        return "newCase";
    }

    @RequestMapping(path="/caseDetails", method = RequestMethod.POST)
    public String caseAdded(@ModelAttribute("caseKey") @Valid CaseModel caseModel,
                            BindingResult bindingResult,
                            Model model){

        LOG.debug(caseModel.toString());

        if (bindingResult.hasErrors()){
            LOG.debug(bindingResult.toString());
            return "newCase";
        }



        return "index";
    }
}

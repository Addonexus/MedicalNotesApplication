package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
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
public class WardController {

    private  CaseServiceInterface caseService;
    private WardServiceInterface wardService;
    private WardRepositoryInterface wardRepository;

    static final Logger LOG = LoggerFactory.getLogger(
            WardController.class
    );

    @Autowired
    public WardController(CaseServiceInterface caseService,
                          WardServiceInterface wardService,
                          WardRepositoryInterface wardRepository){
        this.caseService = caseService;
        this.wardService = wardService;
        this.wardRepository = wardRepository;
    }

    @PostMapping(path="/updateWard")
    public String updateWard(Long wardId, Model model){



        return "home";
    }

    @GetMapping(path ="/ward/{wardIndex}")
    public String getCases(@PathVariable(name="wardIndex")
                           Long wardId, Model model) {

        List<CaseModel> returnedCases =
                caseService.findCasesByWardId(wardId);
        log.debug("CASES RETURNED HOPEFULLY: "
        + returnedCases);


        model.addAttribute("returnedCases ", returnedCases);
        model.addAttribute("wardName", wardService.findById(wardId).get()
        .getName());

        return "main/ward";
    }



}

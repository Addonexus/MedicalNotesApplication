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

    private  CaseServiceInterface caseServiceInterface;
    //private WardServiceInterface wardServiceInterface;
    //private WardRepositoryInterface wardRepositoryInterface;
    private DiagnosisServiceInterface diagnosisService;
    private NotificationServiceInterface notificationService;
    private DiagnosisRepositoryInterface diagnosisRepository;
    private CategoryServiceInterface categoryService;
    private DiagnosisInformationRepositoryJDBC diagnosisInformationRepositoryJDBC;


    @Autowired
    public WardController(CaseServiceInterface caseServiceInterface){
        this.caseServiceInterface = caseServiceInterface;
    }

    @GetMapping(path ="/ward/{wardIndex}")
    public String returnCases(@PathVariable(
            name="wardIndex")Long wardId, Model model){

        return "main/ward";

    }
}

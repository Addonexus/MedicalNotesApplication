package nsa.group4.medical.controllers;


import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.service.DiagnosisServiceInterface;
import nsa.group4.medical.web.CaseForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@Validated
public class CaseController {

    private CaseServiceInterface caseService;

    private DiagnosisServiceInterface diagnosisService;//replace with the service class for diagnosis when implemented

    public CaseController(CaseServiceInterface caseService, DiagnosisServiceInterface diagnosisService){
        this.caseService = caseService;
        this.diagnosisService = diagnosisService;

    }

    static final Logger LOG = LoggerFactory.getLogger(CaseController.class);

    @RequestMapping(path="/createNewCase", method = RequestMethod.GET)
    public String createNewCase(Model model){

        model.addAttribute("caseKey", new CaseForm());
        return "newCase";
    }

    @RequestMapping(path="/caseDetails", method = RequestMethod.POST)
    public String caseAdded(@ModelAttribute("caseKey") @Valid CaseForm caseForm,
                            BindingResult bindingResult,
                            Model model){

        LOG.debug(caseForm.toString());

        if (bindingResult.hasErrors()){
            LOG.debug(bindingResult.toString());
            return "newCase";
        }

        String diagnoses = caseForm.getDiagnosesList();

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
        List<Diagnosis> storingDiagnosis = notExistingDiagnosis.stream().map(x -> new Diagnosis(x)).collect(Collectors.toList());
        CaseModel caseModel = new CaseModel(caseForm.getName(), caseForm.getDemographics());

//      storing both diagnosis list objects into the case diagnosis list
        caseModel.getDiagnosesList().addAll(storingDiagnosis);
        caseModel.getDiagnosesList().addAll(existingDiagnosis);
        caseService.createCase(caseModel);

        return "newCase";//redirect to the case page that has just been created
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

    @GetMapping(path ="/cases/{diagnosisIndex}")
    public String getCases(@PathVariable(name="diagnosisIndex") Long index, Model model){
        List<CaseModel> returnedCases = caseService.findCasesByDiagnosisId(index);
        if(returnedCases.isEmpty()){
        return "404";
        }
        else{
        model.addAttribute("cases", returnedCases);
        return "cases";
        }
    }
}

package nsa.group4.medical.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.service.CaseService;
import nsa.group4.medical.service.CaseServiceInterface;
import nsa.group4.medical.service.DiagnosisService;
import nsa.group4.medical.service.DiagnosisServiceInterface;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@Slf4j
public class RESTController {

    private CaseServiceInterface caseServiceInterface;
    private CategoriesRepositoryJPA categoriesRepository;
    private DiagnosisServiceInterface diagnosisService;


    public RESTController(CaseServiceInterface caseServiceInterface,
                          CategoriesRepositoryJPA categoriesRepository,
                          DiagnosisServiceInterface diagnosisService
                          ){
        this.caseServiceInterface =caseServiceInterface;
        this.categoriesRepository=categoriesRepository;
        this.diagnosisService=diagnosisService;


    }

    @GetMapping("/getAllDiagnosis")
    public @ResponseBody  List<Diagnosis> getDiagnoses(){
        log.debug("REST API RETURN: ");
        List<Diagnosis> returnedList = diagnosisService.getAllDiagnosis();
        log.debug("Returned LIST: "+returnedList);
//        for(Diagnosis diagnosis:returnedList){
//            for(CaseModel caseModel: diagnosis.getCases()){
//                caseModel.setDiagnosesList(null);
//            }
//        }
//        returnedList.forEach(x-> x.getCases().forEach(y -> y.setDiagnosesList(new ArrayList<>())));
        return returnedList;

    }

    @GetMapping("/ajaxtest")
    public @ResponseBody String getAnson() {
        return "anson";
    }
}

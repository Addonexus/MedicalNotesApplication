package nsa.group4.medical.controllers.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
//    @ResponseStatus(value = HttpStatus.OK)
    @RequestMapping(value = "/saveDiagnosis", method = POST, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<?> getAnson(@Valid @RequestBody Form formData, Errors bindingResult) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Rerturned DATA:" +formData);
//        for (String name: formData.get
//        Form test = mapper.treeToValue(formData, Form.class);
//        System.out.println("TSETING"+ test);
//        formData.forEach(x->x.toString());
//        Diagnoses()
//             ) {
//            System.out.println("NICE:"+name);
//
//        }

//        System.out.println(formData.getDiagnoses().toString());
//        formData.forEach(x-> System.out.println("DATA: " + x.getTag()));
//        formData.getDiagnoses().replaceAll("[,"");
        AjaxResponseBody result = new AjaxResponseBody();
        if (bindingResult.hasErrors()) {
            log.debug("BINDING ERROS" + bindingResult.toString());

            // get all errors
            result.setMsg(bindingResult.getAllErrors()
                    .stream()
                    .map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }
        result.setMsg("success");
        return ResponseEntity.ok(result);
    }

}


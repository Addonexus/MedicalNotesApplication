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
import nsa.group4.medical.web.CaseForm;
import org.apache.catalina.User;
import org.apache.catalina.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
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
    @RequestMapping(value = "/saveDiagnosis/{categoryIndex}", method = POST, produces = "application/json")
//    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody ResponseEntity<?> getForm(@PathVariable(name="categoryIndex") Long categoryId,
                                                    @Valid @RequestBody CaseForm formData,
                                                    Errors bindingResult) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Rerturned DATA:" +formData);

        System.out.println("DIAGLIST: " + formData.getDiagnosesList());
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
            //refactoring how error is returned
            result.setStatus("FAIL");
            List<FieldError> allErrors = bindingResult.getFieldErrors();
            List<DiagnosisFieldsError> errorMessages = new ArrayList<>();

            for (FieldError objectError : allErrors) {
                errorMessages.add(new DiagnosisFieldsError(objectError.getField(), objectError.getField() + "  " + objectError.getDefaultMessage()));
            }
            result.setResult(errorMessages);

            log.debug("RETURNED ERRORS: " + result);
            return ResponseEntity.badRequest().body(result);

        }
        else {
        result.setStatus("SUCCESS");
            return ResponseEntity.ok(result);
        }
//        return ResponseEntity.ok(result);
    }

}


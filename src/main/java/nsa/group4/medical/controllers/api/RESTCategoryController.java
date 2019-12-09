package nsa.group4.medical.controllers.api;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.service.implementations.CategoryServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class RESTCategoryController {

    private CaseServiceInterface caseServiceInterface;
    private CategoryServiceInterface categoryService;

    public RESTCategoryController(CaseServiceInterface caseServiceInterface,
                                  CategoryServiceInterface categoryService){
        this.caseServiceInterface =caseServiceInterface;
        this.categoryService=categoryService;
    }

    @DeleteMapping("/deleteCategory/{index}")
    public @ResponseBody ResponseEntity<?> deleteCategoryById(
            @PathVariable Long index, HttpServletRequest request
    ){
        Optional<Categories> returnedCategory = categoryService.findById(index);
        AjaxResponseBody response = new AjaxResponseBody();
        if(returnedCategory.isPresent()) {
            categoryService.deleteById(returnedCategory.get().getId());
            caseServiceInterface.checkEmptyDiagnosis();
            response.setStatus("SUCCESS");
            response.setRedirectUrl("/home");
            return ResponseEntity.ok().body(response);
        }
        response.setStatus("FAILURE");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping(value = "/createCategory", produces = "application/json")
    public @ResponseBody ResponseEntity<?> saveCategory(@RequestBody Map<String, String> formData, Errors bindingResult) {
        System.out.println(formData.get("name"));
        String categoryName = formData.get("name");
        categoryService.saveCategory(new Categories(categoryName));
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setStatus("SUCCESS");
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/getAllCategories")
    public @ResponseBody List<Categories> getCategories(){
        log.debug("REST API RETURN: ");
        List<Categories> returnedList = categoryService.findAll();
        return returnedList;
    }
}


package nsa.group4.medical.controllers.api;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.Helper.Helpers;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.UserService;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.service.implementations.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    public UserService userService;

    public Helpers helpers;

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

    @PostMapping(value = "/updateCategory/{index}", produces = "application/json")
    public @ResponseBody ResponseEntity<?> updateCategory(@PathVariable Long index, @RequestBody Map<String, String> formData, Errors bindingResult) {
        String newName = formData.get("newName");
//        String newCategory = formData.get("newCategory");
//        Diagnosis diagnosis = diagnosisService.getByDiagnosisId(index).get();
        AjaxResponseBody responseBody = new AjaxResponseBody();
        Optional<Categories> returnedCategory = categoryService.findById(index);
        if (returnedCategory.isPresent()) {
            Categories category = returnedCategory.get();
            // the new category name has a name that already exists inside the database
            if (!category.getName().toLowerCase().equals(newName.toLowerCase())) {
                Optional<Categories> checkCategoryNameAlreadyExists = categoryService.findByName(newName);
                if (checkCategoryNameAlreadyExists.isPresent()) {
                    // return a bad response indicating that the update didn't go through due to the name already existing
                    responseBody.setStatus("Name Already Exists");
                    return ResponseEntity.badRequest().body(responseBody);
                } else {
                    // if the new name doesn't already exist in the database then the name is changed and category object is saved
                    category.setName(newName);
                    categoryService.saveCategory(category);
                    responseBody.setStatus("SUCCESS");
                    return ResponseEntity.ok().body(responseBody);
                }
            }
        }
        // something went very wrong when updating the category
        responseBody.setStatus("FAILURE");
        return ResponseEntity.ok().body(responseBody);
    }

    @PostMapping(value = "/createCategory", produces = "application/json")
    public @ResponseBody ResponseEntity<?> saveCategory(@RequestBody Map<String, String> formData, Errors bindingResult) {
        System.out.println(formData.get("name"));
        String categoryName = formData.get("name");
        categoryService.saveCategory(new Categories(categoryName, getUser()));
        AjaxResponseBody responseBody = new AjaxResponseBody();
        responseBody.setStatus("SUCCESS");
        return ResponseEntity.ok().body(responseBody);
    }

    @GetMapping("/getAllCategories")
    public @ResponseBody List<Categories> getCategories(){
        return categoryService.findByUser(getUser());
    }

    private User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(((UserDetails)principal).getUsername());
    }
}


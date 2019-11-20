package nsa.group4.medical.controllers;

import jdk.jfr.Category;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.web.CaseForm;
import nsa.group4.medical.web.CategoriesForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class CategoriesController {

    private CategoriesRepositoryJPA categoriesRepositoryJPA;

    @Autowired
    public CategoriesController(CategoriesRepositoryJPA categoriesRepositoryJPA){
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;

    }

    static final Logger LOG =
            LoggerFactory.getLogger(CategoriesController.class);

    @RequestMapping(path="/createNewCategory",
    method = RequestMethod.GET)
    public String createNewCategory(Model model){
        model.addAttribute("categoryKey" ,
                new Categories());
        return "newCategories";
    }

    @RequestMapping(path="/categoryDetails",
    method = RequestMethod.POST)
    public String category(@ModelAttribute("categoryKey") Categories categories,
                           BindingResult bindingResult,
                           Model model) {
        categoriesRepositoryJPA.save(categories);

        return "newCategories";
    }

    @GetMapping("/{index}")
    public String getCategories(Model model, @PathVariable final Long index) {
        List<Categories> categories = categoriesRepositoryJPA.findAll();
        model.addAttribute("categories", categories);
        return "index";
    }


}

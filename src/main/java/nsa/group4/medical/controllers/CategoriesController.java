package nsa.group4.medical.controllers;

import jdk.jfr.Category;
import nsa.group4.medical.web.CaseForm;
import nsa.group4.medical.web.CategoriesForm;
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
public class CategoriesController {

    static final Logger LOG =
            LoggerFactory.getLogger(CategoriesController.class);

    @RequestMapping(path="/createNewCategory",
    method = RequestMethod.GET)
    public String createNewCategory(Model model){
        model.addAttribute("CategoryKey" ,
                new CategoriesForm());
        return "newCategory";
    }

    @RequestMapping(path="/categoryDetails",
    method = RequestMethod.POST)
    public String category(@ModelAttribute("CategoryKey")
                           @Valid CategoriesForm categoriesForm,
                           BindingResult bindingResult,
                           Model model) {
        LOG.debug(categoriesForm.toString());

        if(bindingResult.hasErrors()) {
            LOG.debug(bindingResult.toString());
            return "newCategory";
        }
        return "newCategory";
    }

}

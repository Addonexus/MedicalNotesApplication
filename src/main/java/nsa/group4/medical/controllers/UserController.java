package nsa.group4.medical.controllers;

//import nsa.group4.medical.data.UserRepositoryJPA;
import nsa.group4.medical.domains.User;
//import nsa.group4.medical.service.SecurityService;
//import nsa.group4.medical.service.UserService;
//import nsa.group4.medical.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SecurityService securityService;
//
//    @Autowired
//    private UserValidator userValidator;
//
//    private UserRepositoryJPA userRepositoryJPA;
//
//    static final Logger LOG = LoggerFactory.getLogger(UserController.class);
//
//    public UserController(UserRepositoryJPA userRepositoryJPA){
//        this.userRepositoryJPA = userRepositoryJPA;
//    }
//
//    @GetMapping("/registration")
//    public String registration(Model model) {
//        model.addAttribute("userForm", new User());
//
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
//        userValidator.validate(userForm, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//
//        System.out.println("aww" + userForm);
//        userRepositoryJPA.save(userForm);
////        userService.save(userForm);
//
//        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
//
//        return "redirect:/welcome";
//    }
//
//    @GetMapping("/login")
//    public String login(Model model, String error, String logout) {
//        if (error != null) {
//            LOG.debug("RELOAD IT");
//            model.addAttribute("error", "Your username and password is invalid.");
//        } else {
//            LOG.debug("NAAAAAHH");
//        }
//
//        System.out.println(userRepositoryJPA.findByUsername("jude"));
//
//        if (logout != null)
//            model.addAttribute( "message", "You have been logged out successfully.");
//
//        return "login";
//    }
//
//    @GetMapping({"/", "/welcome"})
//    public String welcome(Model model) {
//        return "welcome";
//    }
}

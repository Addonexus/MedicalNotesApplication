package nsa.group4.medical.controllers;

import nsa.group4.medical.data.UserRepository;
import nsa.group4.medical.domains.Role;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.service.SecurityService;
import nsa.group4.medical.service.UserService;
import nsa.group4.medical.validator.UserValidator;
import nsa.group4.medical.web.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());

        return "registration";
    }

    @GetMapping("/logout")
    public String logout(){
        return "logoutPage";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult) {

        Set<Role> temp = new HashSet<Role>();

        temp.add(new Role(
                null,
                "admin"
        ));

        User tempUser = new User(
                null,
                userForm.getUsername(),
                userForm.getPassword(),
                userForm.getPassword(),
                temp
        );

        userValidator.validate(tempUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(tempUser);

        securityService.autoLogin(tempUser.getUsername(), tempUser.getPasswordConfirm());

        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

}

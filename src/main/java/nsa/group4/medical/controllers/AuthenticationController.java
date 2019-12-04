package nsa.group4.medical.controllers;
import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.UserRepositoryJPA;
import nsa.group4.medical.domains.Role;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.web.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Slf4j
@Controller
public class AuthenticationController {

    private UserRepositoryJPA userRepositoryJPA;

    @Autowired
    public AuthenticationController(UserRepositoryJPA userRepositoryJPA){this.userRepositoryJPA = userRepositoryJPA;}

    @GetMapping(value = "/login1")
    public String login(Model model) {
        //ModelAndView modelAndView = new ModelAndView();
        //modelAndView.setViewName("login1"); // resources/template/login.html
        model.addAttribute("accountKey", new UserForm());
        return "login1";
    }

    @PostMapping(value = "/login1")
    public String loggingIn(@ModelAttribute("accountKey") @Valid UserForm userForm,
                            BindingResult bindingResult,
                            Model model){
        if (bindingResult.hasErrors()){
            log.debug("AHHHHHHHHHHHHHHHHHHH");
        }

//        User user = new User(
//                userForm.getFirstName(),
//                userForm.getSurnameName(),
//                userForm.getEmail(),
//                userForm.getPassword(),
//                null,
//                null
//        );

        return "welcome";
    }

    @RequestMapping(value = "/register1", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register1"); // resources/template/register.html
        return modelAndView;
    }

    @PostMapping("/register1")
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult) {
        //userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "register1";
        }

        System.out.println("aww" + user);
        userRepositoryJPA.save(user);
//        userService.save(userForm);

        //securityService.autoLogin(user.getUsername(), user.getPasswordConfirm());

        return "redirect:/welcome";
    }

    //@RequestMapping(value = "/home", method = RequestMethod.GET)
    //public ModelAndView home() {
    //    ModelAndView modelAndView = new ModelAndView();
    //    modelAndView.setViewName("home"); // resources/template/home.html
    //    return modelAndView;
    //}
}

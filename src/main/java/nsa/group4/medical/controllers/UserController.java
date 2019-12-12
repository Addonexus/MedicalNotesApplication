package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.UserRepository;
import nsa.group4.medical.data.WardRepositoryJDBC;
import nsa.group4.medical.domains.Role;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.domains.Ward;
import nsa.group4.medical.service.SecurityService;
import nsa.group4.medical.service.UserService;
import nsa.group4.medical.validator.UserValidator;
import nsa.group4.medical.web.UserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
public class UserController {

    private WardRepositoryJDBC wardRepositoryJDBC;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    public UserController(WardRepositoryJDBC wardRepositoryJDBC) {
        this.wardRepositoryJDBC = wardRepositoryJDBC;
    }


    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());

        return "registration";
    }

    @GetMapping("/logout")
    public String logout() throws ServletException {

        httpServletRequest.logout();

        return "login";
    }

    @GetMapping("/accountDetails")
    public String getAccountDetails(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = "";
        Long id = null;
        Long wardId = null;
        if (principal instanceof UserDetails){
            log.debug("HEEHEE");
            username = ((UserDetails)principal).getUsername();
            UserDetails obj = (UserDetails)principal;
            log.debug("THIS IS NIVE: " + obj.toString());
            log.debug("THIS S: " + username);
            User returnedUser = userService.findByUsername(username);
            log.debug("WHADADP: "+returnedUser.getId());
            User user = userService.findByUsername(username);
            id = user.getId();
            wardId = user.getWardId();
        } else {
            log.debug("OOOOOO");
            username = principal.toString();
        }

        model.addAttribute("usernameKey", username);
        model.addAttribute("idKey", id);

        return "accountDetails";
    }

    @GetMapping("/account")
    public String account(Model model){

        List<Ward> wardList = wardRepositoryJDBC.getAllWards();

        model.addAttribute("wardKey", wardList);

        return "accountPage";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult bindingResult) {
        log.debug("WE ARE HERE");
        Set<Role> temp = new HashSet<Role>();
//TODO: check if the role already exists then add it to the user
//        otherwise create a new user role
        temp.add(new Role(
                null,
                "admin"
        ));

        if(userExists(userForm.getUsername())) {
            //add error message

            System.out.println("user already exists");

        }
        else {
            User tempUser = new User(
                    null,
                    userForm.getUsername(),
                    userForm.getPassword(),
                    userForm.getPassword(),
                    temp,
                    new Long(1)
            );

            userValidator.validate(tempUser, bindingResult);

            if (bindingResult.hasErrors()) {
                return "registration";
            }

            userService.save(tempUser);

            securityService.autoLogin(tempUser.getUsername(), tempUser.getPasswordConfirm());
            return "redirect:/home";
        }


        return "/registration";

    }


    @GetMapping("/login")
    public String login(Model model, String error, String logout) {

        log.debug("WE ARE HERE");
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

    private boolean userExists(String username){
        User user = userService.findByUsername(username);
        if(user != null){
            return true;
        }
        return false;
    }
}

package nsa.group4.medical.controllers;
import nsa.group4.medical.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    @Autowired
    public AuthenticationController(){};

    @GetMapping(value = "/login1")
    public String login() {
        //ModelAndView modelAndView = new ModelAndView();
        //modelAndView.setViewName("login1"); // resources/template/login.html
        return "login1";
    }

    @RequestMapping(value = "/register1", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("register1"); // resources/template/register.html
        return modelAndView;
    }

    //@RequestMapping(value = "/home", method = RequestMethod.GET)
    //public ModelAndView home() {
    //    ModelAndView modelAndView = new ModelAndView();
    //    modelAndView.setViewName("home"); // resources/template/home.html
    //    return modelAndView;
    //}
}

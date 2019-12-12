package nsa.group4.medical.controllers;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.DiagnosisInformationRepositoryJDBC;
import nsa.group4.medical.data.NotificationRepoJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.UserService;
import nsa.group4.medical.service.implementations.*;
import nsa.group4.medical.web.UserForm;
import nsa.group4.medical.web.WardForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class WardController {

    @Autowired
    private UserService userService;

    private  CaseServiceInterface caseService;
    private WardServiceInterface wardService;
    private WardRepositoryInterface wardRepository;

    static final Logger LOG = LoggerFactory.getLogger(
            WardController.class
    );

    @Autowired
    public WardController(CaseServiceInterface caseService,
                          WardServiceInterface wardService,
                          WardRepositoryInterface wardRepository){
        this.caseService = caseService;
        this.wardService = wardService;
        this.wardRepository = wardRepository;
    }

    @PostMapping(path="/updateWard")
    public String updateWard(@ModelAttribute("wardForm") @Valid WardForm wardForm,
                             Model model){

        log.debug(wardForm.getWardName());
        Ward tempWard = wardService.findByName(wardForm.getWardName()).get();
        User user = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        Long id = null;
        Long wardId = null;
        if (principal instanceof UserDetails){
            username = ((UserDetails)principal).getUsername();
            UserDetails obj = (UserDetails)principal;
            User returnedUser = userService.findByUsername(username);
            user = userService.findByUsername(username);
            id = user.getId();
            wardId = user.getWardId();
        } else {
            log.debug("OOOOOO");
            username = principal.toString();
        }

        userService.setWardIdForUser(tempWard.getId(), user.getId());

//        model.addAttribute("usernameKey", username);
//        model.addAttribute("idKey", id);

        return "home";
    }

    @GetMapping(path ="/ward/{wardIndex}")
    public String getCases(@PathVariable(name="wardIndex")
                           Long wardId, Model model) {

        List<CaseModel> returnedCases =
                caseService.findCasesByWardId(wardId);



        model.addAttribute("returnedCases ", returnedCases);
        model.addAttribute("wardName", wardService.findById(wardId).get()
        .getName());

        return "main/ward";
    }



}

package nsa.group4.medical.Helper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class Helpers {

    @Autowired
    public UserService userService;

    public Helpers(UserService userService){
     this.userService = userService;
    }

    public User getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        log.debug("WHAT IS GOING ON" + username);
        User returnedUser = userService.findByUsername(username);
        System.out.println(returnedUser);
        return returnedUser;
    }
}

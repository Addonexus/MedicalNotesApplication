package nsa.group4.medical.Helper;

import nsa.group4.medical.domains.User;
import nsa.group4.medical.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



public class Helpers {

    @Autowired
    public UserService userService;

    public User getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        User returnedUser = userService.findByUsername(username);
        System.out.println(returnedUser);
        return returnedUser;
    }
}

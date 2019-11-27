package nsa.group4.medical.config;



import nsa.group4.medical.data.UserRepository;
import nsa.group4.medical.data.UserRolesRepository;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.web.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserRolesRepository userRolesRepository;
  @Autowired
  private PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) {

    System.out.println("password encoded = " + encoder.encode("password"));


    User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    } else {
      System.out.println("User = " + user);

      List<String> userRoles = userRolesRepository.findRoleByUsername(username);
      return new MyUserPrincipal(user, userRoles);
    }
  }

  public User registerNewUserAccount(UserForm userForm) {

    User newUser = new User(null, userForm.getUsername(), userForm.getPassword());


    return newUser;

  }

}

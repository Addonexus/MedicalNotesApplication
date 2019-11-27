//package nsa.group4.medical.service;
//
//import nsa.group4.medical.data.RoleRepositoryJPA;
//import nsa.group4.medical.data.UserRepositoryJPA;
//import nsa.group4.medical.domains.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.context.annotation.Bean;
//
//import java.util.HashSet;
//
//
//@Service
//public class UserServiceImpl implements UserService {
//
////    @Autowired
////    private UserRepositoryJPA userRepository;
////    @Autowired
////    private RoleRepositoryJPA roleRepository;
////    @Autowired
////    private BCryptPasswordEncoder bCryptPasswordEncoder;
////
////
////
////    @Override
////    public void save(User user) {
////        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
////        user.setType(1);
//////        user.setRoles(new HashSet<>(roleRepository.findAll()));
////        userRepository.save(user);
////    }
////
////    @Override
////    public User findByUsername(String username) {
////        return userRepository.findByUsername(username);
////    }
//}

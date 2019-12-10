//package nsa.group4.medical.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * login and registration references from https://www.youtube.com/watch?v=xRE12Y-PFQs&fbclid=IwAR3-l6GCMJPps-ZFHic3QBJOnq6QXwVM3_jt0VLA3H2FfBxH20oIlYjr-H8
// */
//
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//    public void addViewControllers(ViewControllerRegistry registry) {
//
//
//        //registry.addViewController("/login").setViewName("login");
//        //registry.addViewController("/403").setViewName("403");
//        //registry.addViewController("/templates").setViewName("forward:/templates/");
//        //  registry.addViewController("/reports").setViewName("forward:/reports/hello.html");
//
//    }
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        return bCryptPasswordEncoder;
//    }
//}

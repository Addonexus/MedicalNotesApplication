package nsa.group4.medical.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {

//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/403").setViewName("403");
       //registry.addViewController("/templates").setViewName("forward:/templates/");


        registry.addViewController("/reports").setViewName("forward:/reports/hello.html");



    }
}

package nsa.group4.medical;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@ComponentScan({"nsa.group4"})
@EnableJpaRepositories({"nsa.group4"})
@EntityScan({"nsa.group4"})
public class CaseADayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaseADayApplication.class, args);
    }

    //might need to remove from here
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

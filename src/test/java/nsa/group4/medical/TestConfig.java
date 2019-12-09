package nsa.group4.medical;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class TestConfig {

    @Bean
    public MethodValidationPostProcessor bean() {
        return new MethodValidationPostProcessor();
    }
}

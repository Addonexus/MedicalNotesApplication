package nsa.group4.medical;

import nsa.group4.medical.controllers.CaseController;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.service.CaseRepositoryInterface;
import nsa.group4.medical.service.CaseService;
import nsa.group4.medical.service.CaseServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Optional;

@Configuration
public class TestConfig {

    @Bean
    public MethodValidationPostProcessor bean() {
        return new MethodValidationPostProcessor();
    }
}

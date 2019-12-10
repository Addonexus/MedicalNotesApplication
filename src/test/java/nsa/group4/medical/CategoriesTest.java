package nsa.group4.medical;
import nsa.group4.medical.controllers.CaseController;
import nsa.group4.medical.controllers.CategoriesController;
import nsa.group4.medical.web.CaseForm;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=CaseADayApplication.class)
@WebMvcTest(controllers = CategoriesController.class)
@Import(TestConfig.class)
public class CategoriesTest {

    @Autowired
    private CategoriesController categoriesController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception{
        assertThat(categoriesController).isNotNull();
    }

    @Test
    public void formPageLoads() throws Exception{
        mockMvc.perform(get("/createNewCategory"))
                .andExpect(status().isOk());
    }


}

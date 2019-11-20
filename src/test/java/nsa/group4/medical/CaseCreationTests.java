package nsa.group4.medical;

import nsa.group4.medical.controllers.CaseController;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes=CaseADayApplication.class)
@WebMvcTest(controllers = CaseController.class)
public class CaseCreationTests {
    @Autowired
    private CaseController caseController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception{
        assertThat(caseController).isNotNull();
    }

    @Test
    public void formPageLoads() throws Exception{
        mockMvc.perform(get("/createNewCase"))
                .andExpect(status().isOk());
    }
}

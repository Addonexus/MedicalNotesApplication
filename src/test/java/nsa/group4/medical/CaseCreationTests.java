package nsa.group4.medical;

import nsa.group4.medical.controllers.CaseController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class CaseCreationTests {
    @Autowired
    private CaseController caseController;

    @Test
    public void contextLoads() throws Exception{
        assertThat(caseController).isNotNull();
    }
}

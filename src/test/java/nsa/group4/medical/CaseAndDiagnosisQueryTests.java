package nsa.group4.medical;

import jdk.jshell.Diag;
import nsa.group4.medical.data.CaseRepositoryJPA;
import nsa.group4.medical.data.DiagnosisRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.service.CaseRepositoryInterface;
import nsa.group4.medical.service.DiagnosisRepositoryInterface;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

@DataJpaTest
@RunWith(SpringRunner.class)
@Transactional
public class CaseAndDiagnosisQueryTests {
    @Autowired
    private CaseRepositoryInterface caseRepository;

    @Autowired
    private DiagnosisRepositoryInterface diagnosisRepository;

    @Autowired
    EntityManager em;

    private CaseModel testCase;
    private CaseModel testCase2;
    private Diagnosis testDiagnosis;
    private Diagnosis testDiagnosis2;
    @Before
    public void setUpOfVariables(){
        testCase = new CaseModel("Testing Name","10 y/o person");

        testCase2 = new CaseModel("Test 2", "This has created new diagnosis folders internally",
                new Diagnosis("Diagnosis 1"));

        testDiagnosis = new Diagnosis("Test Diagnosis 1");
        testDiagnosis2 = new Diagnosis("Test Diagnosis 2");
    }
    @Test
    public void testingAnInsertOfCaseWithNoDiagnosis(){
        caseRepository.save(testCase);
        CaseModel returnedCase = caseRepository.findByName("Testing Name").get();
        assertNotNull(returnedCase);
        assertEquals(returnedCase.getDemographics(),"10 y/o person") ;
        assertTrue(returnedCase.getDiagnosesList().isEmpty());
    }
    @Test
    public void testingIfAssertionIsFalseIfCaseNotSaved(){
//        CaseModel testCase = new CaseModel("Testing Name","10 y/o person");

        assertFalse(caseRepository.findByName("Testing Name").isPresent());
    }
    @Test
    public void addingADiagnosisInternallyCaseAndStoringBothToDatabase(){
        caseRepository.save(testCase2);
        CaseModel returnedCase = caseRepository.findByName("Test 2").get();
        Diagnosis returnedDiagnosis = diagnosisRepository.findByName("Diagnosis 1").get();
        assertNotNull(returnedCase);
        assertNotNull(returnedDiagnosis);
        assertTrue(returnedCase.getDiagnosesList().contains(returnedDiagnosis));
    }

    @Test
    public void addingAnExistingDiagnosisObjectToACase(){
        Diagnosis returnedDiagnosis = diagnosisRepository.findByName("heart ache").get();
        assertNotNull(returnedDiagnosis);

        testCase.getDiagnosesList().add(returnedDiagnosis);
        caseRepository.save(testCase);
        CaseModel returnedCase = caseRepository.findByName("Testing Name").get();
        assertNotNull(returnedCase);

        assertTrue(returnedCase.getDiagnosesList().contains(returnedDiagnosis));
    }

    @Test
    public void addingAnExistingDiagnosisAndANewDiagnosisObjectsToACase(){
        Diagnosis returnedDiagnosis = diagnosisRepository.findByName("heart ache").get();
        assertNotNull(returnedDiagnosis);

        testCase2.getDiagnosesList().add(returnedDiagnosis);
        caseRepository.save(testCase2);
        CaseModel returnedCase = caseRepository.findByName("Test 2").get();
        assertNotNull(returnedCase);

        Diagnosis returnedCreatedDiagnosis = diagnosisRepository.findByName("Diagnosis 1").get();
        assertNotNull(returnedCreatedDiagnosis);

        assertTrue(returnedCase.getDiagnosesList().contains(returnedDiagnosis));
        assertTrue(returnedCase.getDiagnosesList().contains(returnedCreatedDiagnosis));
    }

    @Test
    @Modifying
    public void ensureDeletingACaseDoesNotDeleteTheDiagnosis(){
        caseRepository.save(testCase2);
        CaseModel returnedCase = caseRepository.findByName("Test 2").get();
        Long diagnosisID = returnedCase.getDiagnosesList().stream().findFirst().get().getId();

        caseRepository.deleteById(returnedCase.getId());

        assertEquals(diagnosisRepository.findById(diagnosisID).get().getName(), "Diagnosis 1");
        assertTrue(caseRepository.findByName("Test 2").isEmpty());

    }


}

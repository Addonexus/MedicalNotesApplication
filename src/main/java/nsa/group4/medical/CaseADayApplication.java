package nsa.group4.medical;


import jdk.jshell.Diag;
import nsa.group4.medical.testing_links.CaseRepository;
import nsa.group4.medical.testing_links.Diagnosis;
import nsa.group4.medical.testing_links.DianoisisRepository;
import nsa.group4.medical.testing_links.ExaminationCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class CaseADayApplication implements CommandLineRunner {
    @Autowired
    private CaseRepository caseRepository;
    @Autowired
    private DianoisisRepository dianoisisRepository;

    public static void main(String[] args) {
        SpringApplication.run(CaseADayApplication.class, args);
    }
    @Modifying
    @Override
    @Transactional
    public void run(String... args) throws Exception {


//        caseRepository.save(new ExaminationCase("cat", "10 y/o", new Diagnosis("blodd")));
//        List<ExaminationCase> cases = caseRepository.findAll();
//        List<Diagnosis> diagnoses = dianoisisRepository.findAll();
//
//
//
//
//
//        System.out.println(cases.toString());
//        System.out.println(diagnoses.toString());
//        System.out.println("=======================================");
////        for (Diagnosis item: diagnoses
////             ) {
////            System.out.println("Diagnosis id: " + item.getId());
////            item.getCases().forEach(i -> System.out.println("Case: " + i));
////
////        }
//        Diagnosis diagnosis1  = new Diagnosis("drops");
//        ExaminationCase case1 = new ExaminationCase("things to test our", "25 y/o male");
//
//        List<Diagnosis> diagnosesList = new ArrayList<>();
//        diagnosesList.add(diagnosis1);
//
////        Optional<Diagnosis> diagnosisOptional = dianoisisRepository.findById(2L);
////        if(diagnosisOptional.isPresent()){
////            diagnosesList.add(diagnosisOptional.get());
////        }
//
//        case1.setDiagnosesList(diagnosesList);

//        caseRepository.save(case1);


//        caseRepository.deleteById(5L);
        System.out.println("performing a deletion of the diagnosis id 2");
        dianoisisRepository.deleteById(2L);

//        diagnoses.forEach(i -> System.out.println(i.getId()+" :!!!!!!!!!: " + i.getCases().forEach(u -> System.out.println(u.toString()))));


    }
}

package nsa.group4.medical.service;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CaseService implements CaseServiceInterface {
    @Autowired
    private CaseRepositoryInterface caseRepository;
    private DiagnosisRepositoryInterface diagnosisRepository;

    public CaseService(CaseRepositoryInterface caseRepository, DiagnosisRepositoryInterface diagnosisRepository){
        this.caseRepository = caseRepository;
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public Optional<CaseModel> findByCaseName(String caseName) {
        return caseRepository.findByName(caseName);
    }

    @Override
    public Optional<CaseModel> findByCaseId(Long Id) {
        return caseRepository.findById(Id);
    }

    @Override
    public void createCase(CaseModel caseModel) {
        caseRepository.save(caseModel);
    }

    @Override
    public List<CaseModel> findAll() {
        return caseRepository.findAll();
    }

    @Override
    public List<CaseModel> findCasesByDiagnosisId(Long index) {
        Optional<Diagnosis> returnedDiagnosis = diagnosisRepository.findById(index);
        log.debug("Internal Query for Diagnosis: " + returnedDiagnosis);
        if (returnedDiagnosis.isPresent()){
            Diagnosis diagnosis = returnedDiagnosis.get();
            log.debug("Inter query for Diagnosis Cases: " + returnedDiagnosis.get().getCases().toString());
            return diagnosis.getCases();
        }else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<CaseModel> findAllByOrderByCreationDate() {
        return caseRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<CaseModel> findAllByOrderByCreationDateAsc() {
        return caseRepository.findAllByOrderByCreationDateDesc();
    }
}

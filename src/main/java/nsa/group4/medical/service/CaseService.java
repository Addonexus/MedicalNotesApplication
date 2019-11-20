package nsa.group4.medical.service;

import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
    public List<CaseModel> findCasesByDiagnosisId(Long index) {
        Optional<Diagnosis> returnedDiagnosis = diagnosisRepository.findById(index);
        if (returnedDiagnosis.isPresent()){
            Diagnosis diagnosis = returnedDiagnosis.get();
            return diagnosis.getCases();
        }
        return new ArrayList<>();
    }
}

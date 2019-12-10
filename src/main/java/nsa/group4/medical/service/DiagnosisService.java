package nsa.group4.medical.service;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.service.implementations.CaseRepositoryInterface;
import nsa.group4.medical.service.implementations.CaseServiceInterface;
import nsa.group4.medical.service.implementations.DiagnosisRepositoryInterface;
import nsa.group4.medical.service.implementations.DiagnosisServiceInterface;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DiagnosisService implements DiagnosisServiceInterface {

    private DiagnosisRepositoryInterface diagnosisRepository;
    private CaseRepositoryInterface caseRepository;
    private CaseServiceInterface caseServiceInterface;
    public DiagnosisService( DiagnosisRepositoryInterface diagnosisRepository
            ,CaseServiceInterface caseServiceInterface,
                             CaseRepositoryInterface caseRepository){
        this.diagnosisRepository = diagnosisRepository;
        this.caseRepository = caseRepository;
        this.caseServiceInterface =caseServiceInterface;
    }


    @Override
    public Optional<Diagnosis> getCaseByDiagnosisName(String name) {
        return diagnosisRepository.findByName(name);
    }

    @Override
    public List<Diagnosis> findByCaseNameIn(Collection<String> names) {
        return diagnosisRepository.findByNameIn(names);
    }

//    @Override
//    public List<Diagnosis> getByCategoryId(Long index) {
//        return diagnosisRepository.findByCategoryId(index);
//    }

    @Override
    public Optional<Diagnosis> getByDiagnosisId(Long index) {
        return diagnosisRepository.findById(index);
    }

    @Override
    public List<Diagnosis> findByCategories(Categories categories) {
        return diagnosisRepository.findByCategories(categories);
    }

    @Override
    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }

    @Override
    public Optional<Diagnosis> findByName(String name) {
        return diagnosisRepository.findByName(name);
    }

    @Override
    public List<Diagnosis> findAll() {
        return diagnosisRepository.findAll();
    }

    @Override
    public void deleteDiagnosisById(Long id) {
        diagnosisRepository.deleteById(id);
//        caseServiceInterface.checkEmptyDiagnosis();

    }

    @Override
    public Optional<Diagnosis> findById(Long diagnosisId) {
        return diagnosisRepository.findById(diagnosisId);
    }
}

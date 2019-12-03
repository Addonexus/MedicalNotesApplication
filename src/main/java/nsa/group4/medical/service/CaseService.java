package nsa.group4.medical.service;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.web.CaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CaseService implements CaseServiceInterface {
    @Autowired
    private CaseRepositoryInterface caseRepository;
    private DiagnosisRepositoryInterface diagnosisRepository;
    private CategoriesRepositoryJPA categoriesRepositoryJPA;

    public CaseService(CaseRepositoryInterface caseRepository,
                       DiagnosisRepositoryInterface diagnosisRepository,
                       CategoriesRepositoryJPA categoriesRepositoryJPA){
        this.caseRepository = caseRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.categoriesRepositoryJPA = categoriesRepositoryJPA;
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
    public void createCase(CaseForm form) {
        //makes a list of all the diagnosis items that were entered by user in the input field
        List<String> diagnosesList = form.getDiagnosesList()
                .stream().map(x -> Objects.toString(x.getTag(), null))
                .collect(Collectors.toList());
        //gets all of the existing diagnosis in the db from the list of diagnosis entered by the user
        List<Diagnosis> existingDiagnosis = diagnosisRepository.findByNameIn(diagnosesList);

        //separates the diagnoses that don't already exist so that we can create new additions to the db
        List<String> notExistingDiagnosis = diagnosesList
                .stream().filter(x -> existingDiagnosis
                .stream().noneMatch(diagnosis -> diagnosis.getName().equals(x)))
                .collect(Collectors.toList());
        //Logic for a new category when a diagnosis doesn't already exist (puts into a "Miscellaneous" category by default)
        boolean categoryExists = categoriesRepositoryJPA.existsByName("Miscellaneous");
        Categories category;
        if (categoryExists){
             category =  categoriesRepositoryJPA.findByName("Miscellaneous").get();
        }
        else{
            //creates the "Miscellaneous" category if it doesn't already exist
             category = categoriesRepositoryJPA.save(new Categories(null, "Miscellaneous", new ArrayList<>()));
        }

        //List of new Diagnosis in the object that will allow them to be stored like the existing Diagnosis List
        List<Diagnosis> newDiagnoses = notExistingDiagnosis.stream().map(x -> new Diagnosis(x, category)).collect(Collectors.toList());

        log.debug("CREATING A NEW CASE");
        CaseModel caseModel = new CaseModel(
                form.getName(),
                form.getDemographics(),
                new ArrayList<>(),
                form.getPresentingComplaint(),
                form.getPresentingComplaintHistory(),
                form.getMedicalHistory(),
                form.getDrugHistory(),
                form.getAllergies(),
                form.getFamilyHistory(),
                form.getSocialHistory(),
                form.getNotes(),
                LocalDateTime.now()
        );
        //storing both diagnosis list objects into the case diagnosis list
        caseModel.getDiagnosesList().addAll(newDiagnoses);
        caseModel.getDiagnosesList().addAll(existingDiagnosis);
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

    @Override
    public void updateCase(CaseForm form) {
        //makes a list of all the diagnosis items that were entered by user in the input field
        List<String> diagnosesList = form.getDiagnosesList()
                .stream().map(x -> Objects.toString(x.getTag(), null))
                .collect(Collectors.toList());
        //gets all of the existing diagnosis in the db from the list of diagnosis entered by the user
        List<Diagnosis> existingDiagnosis = diagnosisRepository.findByNameIn(diagnosesList);

        //separates the diagnoses that don't already exist so that we can create new additions to the db
        List<String> notExistingDiagnosis = diagnosesList
                .stream().filter(x -> existingDiagnosis
                        .stream().noneMatch(diagnosis -> diagnosis.getName().equals(x)))
                .collect(Collectors.toList());
        //Logic for a new category when a diagnosis doesn't already exist (puts into a "Miscellaneous" category by default)
        boolean categoryExists = categoriesRepositoryJPA.existsByName("Miscellaneous");
        Categories category;
        if (categoryExists){
            category =  categoriesRepositoryJPA.findByName("Miscellaneous").get();
        }
        else{
            //creates the "Miscellaneous" category if it doesn't already exist
            category = categoriesRepositoryJPA.save(new Categories(null, "Miscellaneous", new ArrayList<>()));
        }

        //List of new Diagnosis in the object that will allow them to be stored like the existing Diagnosis List
        List<Diagnosis> newDiagnoses = notExistingDiagnosis.stream().map(x -> new Diagnosis(x, category)).collect(Collectors.toList());

        log.debug("ID IS NOT NULL THEREFORE UPDATING THE CASE");
                CaseModel caseModel = caseRepository.findById(form.getId()).get();
                caseModel.setName(form.getName());
                caseModel.setDemographics(form.getDemographics());
                caseModel.setAllergies(form.getAllergies());
                caseModel.setPresentingComplaint(form.getPresentingComplaint());
                caseModel.setPresentingComplaintHistory(form.getPresentingComplaintHistory());
                caseModel.setDrugHistory(form.getDrugHistory());
                caseModel.setMedicalHistory(form.getMedicalHistory());
                caseModel.setSocialHistory(form.getSocialHistory());
                caseModel.setFamilyHistory(form.getFamilyHistory());
                caseModel.setNotes(form.getNotes());

        //storing both diagnosis list objects into the case diagnosis list
        //sets a new diagnosis list to replace what was already there
        caseModel.setDiagnosesList(newDiagnoses);
        caseModel.getDiagnosesList().addAll(existingDiagnosis);
        caseRepository.save(caseModel);
    }

    @Override
    public List<CaseModel> findByCreationDateBetween(LocalDateTime creationDate, LocalDateTime creationDate2) {
        return caseRepository.findByCreationDateBetween(creationDate, creationDate2);
    }
}

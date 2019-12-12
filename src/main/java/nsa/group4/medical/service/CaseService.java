package nsa.group4.medical.service;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.NotificationRepoJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.implementations.*;
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
    private CategoryRepositoryInterface categoryRepository;
    private NotificationServiceInterface notificationService;
    private WardRepositoryInterface wardRepository;

    public CaseService(CaseRepositoryInterface caseRepository,
                       DiagnosisRepositoryInterface diagnosisRepository,
                       CategoryRepositoryInterface categoryRepository,
                       NotificationServiceInterface notificationService,
                       WardRepositoryInterface wardRepository){
        this.caseRepository = caseRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.categoryRepository = categoryRepository;
        this.notificationService = notificationService;
        this.wardRepository = wardRepository;
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
        boolean categoryExists = categoryRepository.existsByName("Miscellaneous");
        Categories category;

        //List of new Diagnosis in the object that will allow them to be stored like the existing Diagnosis List

        log.debug("CREATING A NEW CASE");
        CaseModel caseModel = new CaseModel(
                form.getName(),
                form.getDemographics(),
                new ArrayList<>(),
                form.getWard(),
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
        List<Diagnosis> newDiagnoses = new ArrayList<>();

        //storing both diagnosis list objects into the case diagnosis list
        if(!notExistingDiagnosis.isEmpty()){

            if(!categoryExists ){
                category = categoryRepository.save(new Categories(null, "Miscellaneous", new ArrayList<>()));
            }
            else{
                category =  categoryRepository.findByName("Miscellaneous").get();
            }

            newDiagnoses = notExistingDiagnosis.stream().map(x -> new Diagnosis(x, category)).collect(Collectors.toList());

            caseModel.getDiagnosesList().addAll(newDiagnoses);
        }
        caseModel.getDiagnosesList().addAll(existingDiagnosis);
        caseRepository.save(caseModel);
        for (Diagnosis diagnosis : newDiagnoses) {
            notificationService.saveNotification(
                    new Notifications(diagnosis)
            );
        }
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
    public List<CaseModel> findCasesByWardId(Long index) {
        Optional<Ward> returnedWard = wardRepository.findById(index);
        log.debug("Internal Query for ward: " + returnedWard);
        if (returnedWard.isPresent()){
            Ward ward = returnedWard.get();
            log.debug("Inter query for ward Cases: " + returnedWard.get().getCases().toString());
            return ward.getCases();
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
        boolean categoryExists = categoryRepository.existsByName("Miscellaneous");
        Categories category;


        log.debug("ID IS NOT NULL THEREFORE UPDATING THE CASE");
        CaseModel caseModel = caseRepository.findById(form.getId()).get();
        caseModel.setName(form.getName());
        caseModel.setDemographics(form.getDemographics());
        caseModel.setWard(form.getWard());
        caseModel.setAllergies(form.getAllergies());
        caseModel.setPresentingComplaint(form.getPresentingComplaint());
        caseModel.setPresentingComplaintHistory(form.getPresentingComplaintHistory());
        caseModel.setDrugHistory(form.getDrugHistory());
        caseModel.setMedicalHistory(form.getMedicalHistory());
        caseModel.setSocialHistory(form.getSocialHistory());
        caseModel.setFamilyHistory(form.getFamilyHistory());
        caseModel.setNotes(form.getNotes());

        if(!notExistingDiagnosis.isEmpty()) {

            if (!categoryExists) {
                category = categoryRepository.save(new Categories(null, "Miscellaneous", new ArrayList<>()));
            } else {
                category = categoryRepository.findByName("Miscellaneous").get();
            }
            //List of new Diagnosis in the object that will allow them to be stored like the existing Diagnosis List
            List<Diagnosis> newDiagnoses = notExistingDiagnosis.stream().map(x -> new Diagnosis(x, category)).collect(Collectors.toList());
            caseModel.setDiagnosesList(newDiagnoses);
            caseModel.getDiagnosesList().addAll(existingDiagnosis);
        }
        //storing both diagnosis list objects into the case diagnosis list
        //sets a new diagnosis list to replace what was already there
        else{
            caseModel.setDiagnosesList(existingDiagnosis);
            log.debug("THERE ARE NO NEW DIAGNOSIS");
        }
        caseRepository.save(caseModel);
    }

    @Override
    public List<CaseModel> findByCreationDateBetween(LocalDateTime creationDate, LocalDateTime creationDate2) {
        return caseRepository.findByCreationDateBetween(creationDate, creationDate2);
    }

    @Override
    public void deleteCaseById(Long id) {
        caseRepository.deleteById(id);
    }

    @Override
    public void checkEmptyDiagnosis(){
        List<CaseModel> listCases = caseRepository.findAll();
        for (CaseModel caseModel:
                listCases) {
            if(caseModel.getDiagnosesList().isEmpty()){
                caseRepository.deleteById(caseModel.getId());
            }

        }
    }
}

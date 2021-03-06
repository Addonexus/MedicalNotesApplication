package nsa.group4.medical.service;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.Helper.Helpers;
import nsa.group4.medical.data.CategoriesRepositoryJPA;
import nsa.group4.medical.data.NotificationRepoJPA;
import nsa.group4.medical.domains.*;
import nsa.group4.medical.service.implementations.*;
import nsa.group4.medical.web.CaseForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
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

    @Autowired
    EntityManager entityManager;

    private WardRepositoryInterface wardRepository;

    private Helpers helpers;

    @Autowired
    private UserService userService;

    public CaseService(CaseRepositoryInterface caseRepository,
                       DiagnosisRepositoryInterface diagnosisRepository,
                       CategoryRepositoryInterface categoryRepository,
                       NotificationServiceInterface notificationService,
                       WardRepositoryInterface wardRepository,
                       Helpers helpers){
        this.caseRepository = caseRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.categoryRepository = categoryRepository;
        this.notificationService = notificationService;
        this.wardRepository = wardRepository;
        this.helpers =helpers;
    }

    @Override
    public Optional<CaseModel> findByCaseName(String caseName) {
        return caseRepository.findByNameAndUser(caseName, helpers.getUserId());
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
        boolean categoryExists = categoryRepository.existsByNameAndUser("Miscellaneous", helpers.getUserId());
        Categories category;

        //List of new Diagnosis in the object that will allow them to be stored like the existing Diagnosis List

        log.debug("CREATING A NEW CASE");
        CaseModel caseModel = new CaseModel(
                null,
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
                LocalDateTime.now(),
                helpers.getUserId()

        );
        List<Diagnosis> newDiagnoses = new ArrayList<>();

        //storing both diagnosis list objects into the case diagnosis list
        if(!notExistingDiagnosis.isEmpty()){

            if(!categoryExists ){
                category = categoryRepository.save(new Categories(
                        "Miscellaneous",
                        getUserId()));
            }
            else{
                category =  categoryRepository.findByName("Miscellaneous").get();
            }

            newDiagnoses = notExistingDiagnosis.stream().map(x -> new Diagnosis(helpers.getUserId(),x, category)).collect(Collectors.toList());

            caseModel.getDiagnosesList().addAll(newDiagnoses);
        }
        caseModel.getDiagnosesList().addAll(existingDiagnosis);
        caseRepository.save(caseModel);
        for (Diagnosis diagnosis : newDiagnoses) {
            notificationService.saveNotification(
                    new Notifications(helpers.getUserId(),diagnosis)
            );
        }
    }

    @Override
    public List<CaseModel> findAll() {
        return caseRepository.findByUser(helpers.getUserId());
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

    public User getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(((UserDetails)principal).getUsername());
    }

    @Override
    public List<CaseModel> findAllByOrderByCreationDate() {

        return caseRepository.findByUserOrderByCreationDateDesc(helpers.getUserId());
    }

    @Override
    public List<CaseModel> findAllByOrderByCreationDateAsc() {
        return caseRepository.findAllByOrderByCreationDateAsc();
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
        boolean categoryExists = categoryRepository.existsByNameAndUser("Miscellaneous",helpers.getUserId());
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
                category = categoryRepository.save(new Categories(null,"Miscellaneous", getUserId(), new ArrayList<>()));
            } else {
                category = categoryRepository.findByName("Miscellaneous").get();
            }
            //List of new Diagnosis in the object that will allow them to be stored like the existing Diagnosis List
            List<Diagnosis> newDiagnoses = notExistingDiagnosis.stream().map(x -> new Diagnosis(helpers.getUserId(),x, category)).collect(Collectors.toList());
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
        return caseRepository.findByUserAndCreationDateBetween(helpers.getUserId(),creationDate, creationDate2);
    }

    @Override
    public void deleteCaseById(Long id) {
        caseRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void checkEmptyDiagnosis(){
        List<CaseModel> listCases = caseRepository.findByUser(helpers.getUserId());
        System.out.println("LIST OF CASES" + listCases);
        for (CaseModel caseModel:
                listCases) {
            if(caseModel.getDiagnosesList().isEmpty()){
//                Query q = entityManager.createQuery("DELETE FROM CaseModel c WHERE c.user = " + helpers.getUserId().getId() + "");
                Query q = entityManager.createQuery("DELETE FROM CaseModel c WHERE c.id = " + caseModel.getId());
                q.executeUpdate();
            }
        }
//        Query q = entityManager.createQuery("DELETE FROM CaseModel c WHERE  c.user = " + helpers.getUserId().getId() + "");

    }
}

package nsa.group4.medical.service;


import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.Helper.Helpers;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Ward;
import nsa.group4.medical.service.implementations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class WardService implements WardServiceInterface {

    @Autowired
    private Helpers helpers;
    private  WardRepositoryInterface wardRepositoryInterface;
    private CaseRepositoryInterface caseRepositoryInterface;
    private CaseServiceInterface caseServiceInterface;

    public WardService (WardRepositoryInterface wardRepositoryInterface,
                        CaseRepositoryInterface caseRepositoryInterface,
                        CaseServiceInterface caseServiceInterface) {
        this.wardRepositoryInterface = wardRepositoryInterface;
        this.caseRepositoryInterface = caseRepositoryInterface;
        this.caseServiceInterface = caseServiceInterface;
    }

    @Override
    public Optional<Ward> getCaseByWardName(String name){
        return wardRepositoryInterface.findByNameAndUser(name,helpers.getUserId());
    }

    @Override
    public List<Ward> findByNameIn(Collection<String> names){
        return wardRepositoryInterface.findByUserAndNameIn(helpers.getUserId(), names);
    }

    @Override
    public Optional <Ward> getByWardId(Long index) {
        return wardRepositoryInterface.findById(index);
    }

    @Override
    public List<Ward> getAllWard(){
        return wardRepositoryInterface.findByUser(helpers.getUserId());
    }

    @Override
    public Optional<Ward> findByName(String name){
        return wardRepositoryInterface.findByNameAndUser(name, helpers.getUserId());
    }

    @Override
    public List<Ward> findAll() {
        return wardRepositoryInterface.findByUser(helpers.getUserId());
    }

    @Override
    public Optional<Ward> findById(Long wardId) {
        return wardRepositoryInterface.findById(wardId);
    }






}

package nsa.group4.medical.service.implementations;

import nsa.group4.medical.service.events.DiagnosisInformationAdded;

public interface DiagnosisInformationRepositoryInterface {
    public void saveDiagnosisInformation(DiagnosisInformationAdded diagnosisInformationEvent);
}

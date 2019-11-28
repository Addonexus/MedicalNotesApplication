package nsa.group4.medical.controllers.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsa.group4.medical.domains.CaseModel;
import nsa.group4.medical.domains.Diagnosis;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResponseBody {
    String status;
    CaseModel caseModel;
    List<Diagnosis> diagnoses;
    List<DiagnosisFieldsError> result;
    String redirectUrl;
    List<CaseModel> casesList;
    List<Long> categoryIds;
}

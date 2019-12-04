package nsa.group4.medical.service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import nsa.group4.medical.domains.DiagnosisInformation;

@Data
@AllArgsConstructor
public class DiagnosisInformationAdded {

    private Long Id;
    private String field;
    private String value;
}

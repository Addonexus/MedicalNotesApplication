package nsa.group4.medical.service.events;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiagnosisInformationAdded {

    private Long donationId;
    private String key;
    private String value;
}

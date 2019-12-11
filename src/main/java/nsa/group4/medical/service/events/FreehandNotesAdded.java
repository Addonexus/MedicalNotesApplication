package nsa.group4.medical.service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import nsa.group4.medical.domains.FreehandNotes;

@Data
@AllArgsConstructor
public class FreehandNotesAdded {

    private Long Id;
    private Long diagnosisId;
    private String field;

}

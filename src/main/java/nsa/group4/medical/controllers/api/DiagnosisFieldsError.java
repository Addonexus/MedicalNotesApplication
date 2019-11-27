package nsa.group4.medical.controllers.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisFieldsError {
    private String fieldName;
    private String errorMessage;
}

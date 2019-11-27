package nsa.group4.medical.controllers.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AjaxResponseBody {
    String status;
    List<DiagnosisFieldsError> result;
    String redirectUrl;
}

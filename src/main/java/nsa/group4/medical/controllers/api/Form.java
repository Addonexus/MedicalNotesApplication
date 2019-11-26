package nsa.group4.medical.controllers.api;

import lombok.Data;
import nsa.group4.medical.domains.Diagnosis;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class Form {
    @NotEmpty
    private ArrayList<ReturnedData> diagnoses;


}

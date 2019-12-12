package nsa.group4.medical.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsa.group4.medical.controllers.api.ReturnedData;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseForm {
    private Long id;
    @NotNull
    @Size(min=2, max=30, message = "Invalid Name")
    private String name;
    @NotNull
    @Size(min=3, message = "Invalid Demographics Name(s) provided")
    private String demographics;

    private String ward;

    private String presentingComplaint;

    private String presentingComplaintHistory;

    private String medicalHistory;

    private String drugHistory;

    private String allergies;

    private String familyHistory;

    private String socialHistory;

    private String notes;

//    @NotNull
//    @Size(min=2, message = "Invalid Diagnosis Name(s) provided")
//    private String diagnosesList;

    @NotEmpty(message = "Diagnosis cannot be empty")
    private ArrayList<ReturnedData> diagnosesList;
}

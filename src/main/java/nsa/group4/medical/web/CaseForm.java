package nsa.group4.medical.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nsa.group4.medical.domains.Categories;
import nsa.group4.medical.domains.Diagnosis;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseForm {

    @NotNull
    @Size(min=2, max=30, message = "Invalid Name")
    private String name;
    @NotNull
    @Size(min=3, message = "Invalid Demographics Name(s) provided")
    private String demographics;

    @NotNull
    @Size(min=2, message = "Invalid Diagnosis Name(s) provided")
    private String diagnosesList;
}

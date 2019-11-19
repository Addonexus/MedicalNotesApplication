package nsa.group4.medical.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaseForm {

    @NotNull
    @Size(min=2, max=30, message = "Invalid Name")
    private String name;

    private String demographics;

    @NotNull
    private String diagnosesList;
}

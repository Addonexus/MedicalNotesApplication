package nsa.group4.medical.web;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisInformationForm {

    @NotNull
    @Size(min=2, max=15, message="Invalid Key")
    private String key;

    @NotNull
    @Size(min=2, max=100, message="Invalid Value")
    private String value;
}

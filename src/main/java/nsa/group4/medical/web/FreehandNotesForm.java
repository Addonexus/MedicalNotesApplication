package nsa.group4.medical.web;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreehandNotesForm {
    @NotNull
    @Size(min=2, max=200, message="Invalid Field")
    private String field;
}

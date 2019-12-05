package nsa.group4.medical.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    private String field;
    private String value;
}

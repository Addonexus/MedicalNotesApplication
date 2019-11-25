package nsa.group4.medical.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisInformation {
    @Id
    @Column(name="diagnosis_id")
    private Long id;

    private String key;

    private String value;
}

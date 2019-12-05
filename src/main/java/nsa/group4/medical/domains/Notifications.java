package nsa.group4.medical.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Notifications {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosisLink;

    private boolean isDone;

    private boolean isRead;

    // Add boolean on (if done)
    // Add boolean on (if read)
    // Add date

    private String content;
}

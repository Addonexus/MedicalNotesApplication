package nsa.group4.medical.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Notifications {
    @Id
    @GeneratedValue
    private Long id;

    // Add diagnosis link
    // Add boolean on (if done)
    // Add boolean on (if read)
    // Add date

    private String content;
}

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
public class Notification {
    @Id
    @GeneratedValue
    private Long id;
    
    private String content;
}

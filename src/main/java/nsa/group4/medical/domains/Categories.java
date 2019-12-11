package nsa.group4.medical.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "categories")
@ToString(exclude = {"diagnosisList", "user"})
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="categories", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Diagnosis> diagnosisList;

    public Categories(String name) {
        this.name = name;
    }
}

package nsa.group4.medical.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "ward")
@ToString(exclude = {"cases", "user"})
public class Ward {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    public Ward(User user, String name){
        this.name = name;
        this.user = user;
    }


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinTable(
            name = "ward_cases_link",
            joinColumns = {@JoinColumn(name="ward_id")},
            inverseJoinColumns = {@JoinColumn(name = "case_id")})
    @JsonManagedReference
    private List<CaseModel> cases;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="user_id")
    private User user;

    public List<CaseModel> getCases() {
        if(cases == null){
            return new ArrayList<>();
        }
        return cases;
    }


}

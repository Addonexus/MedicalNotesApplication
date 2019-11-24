package nsa.group4.medical.domains;

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
@Table(name= "diagnoses")
@ToString(exclude = "cases")
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    @JoinTable(
            name = "cases_diagnoses_link",
            joinColumns = {@JoinColumn(name="diagnosis_id")},
            inverseJoinColumns = {@JoinColumn(name = "case_id")})
    @JsonManagedReference
    private List<CaseModel> cases;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="category_id")
    private Categories categories;

    public Diagnosis(String name){
        this.name = name;
    }

    public Diagnosis(String name, Categories category){
        this.name = name;
        this.categories = category;
    }
//    @JsonIgnore
//    @JsonManagedReference
    // you need this when trying to stream through the list of cases from the diagnosis to print or manipulate
    public List<CaseModel> getCases() {
        if(cases == null){
            return new ArrayList<>();
        }
        return cases;
    }

}

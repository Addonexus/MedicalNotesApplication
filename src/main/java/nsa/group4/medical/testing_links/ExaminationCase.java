package nsa.group4.medical.testing_links;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cases")
public class ExaminationCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;

    @Column(name="demographics")
    private String demographics;
    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
            CascadeType.SAVE_UPDATE,})
    @JoinTable(
            name = "cases_diagnoses_link",
            joinColumns = {@JoinColumn(name="case_id",
                    nullable = false,
                    updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "diagnosis_id",
                    nullable = false,
                    updatable = false)},
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private List<Diagnosis> diagnosesList;

    public ExaminationCase(String name, String demographics, Diagnosis... diagnoses){
        this.name = name;
        this.demographics = demographics;
        this.diagnosesList = Stream.of(diagnoses).collect(Collectors.toList());
        this.diagnosesList.forEach(diagnosis -> diagnosis.getCases().add(this));
    }

    public List<Diagnosis> getDiagnosesList() {
        if(diagnosesList == null){
            return new ArrayList<>();
        }
        return diagnosesList;
    }
}

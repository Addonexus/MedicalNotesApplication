package nsa.group4.medical.testing_links;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name="category_id")
    private Long categoryId = 1L;

    @ManyToMany( fetch = FetchType.LAZY)
    @Cascade({
            org.hibernate.annotations.CascadeType.DETACH,
            org.hibernate.annotations.CascadeType.MERGE,
            org.hibernate.annotations.CascadeType.REFRESH,
            org.hibernate.annotations.CascadeType.PERSIST,
            org.hibernate.annotations.CascadeType.SAVE_UPDATE,})
    @JoinTable(
            name = "cases_diagnoses_link",
            joinColumns = {@JoinColumn(name="diagnosis_id",
                    nullable = false,
                    updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "case_id",
                    nullable = false,
                    updatable = false)},
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private List<ExaminationCase> cases;

    public Diagnosis(String name){
        this.name = name;
    }


    public List<ExaminationCase> getCases() {
        if(cases == null){
            return new ArrayList<>();
        }
        return cases;
    }
}

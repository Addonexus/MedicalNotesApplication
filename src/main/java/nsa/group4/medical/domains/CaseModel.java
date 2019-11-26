package nsa.group4.medical.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cases")
public class CaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;

    @Column(name="demographics")
    private String demographics;
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST},
            fetch = FetchType.LAZY)
    @JoinTable(
            name = "cases_diagnoses_link",
            joinColumns = {@JoinColumn(name="case_id")},
            inverseJoinColumns = {@JoinColumn(name = "diagnosis_id")}
            )
    @JsonBackReference
    private List<Diagnosis> diagnosesList;

    public CaseModel(String name, String demographics, Diagnosis... diagnoses){
        this.name = name;
        this.demographics = demographics;
        this.diagnosesList = Stream.of(diagnoses).collect(Collectors.toList());
        this.diagnosesList.forEach(diagnosis -> diagnosis.getCases().add(this));
    }
//    @JsonIgnore

    public List<Diagnosis> getDiagnosesList() {
        if(diagnosesList == null){
            return new ArrayList<>();
        }
        return diagnosesList;
    }

    public String formatDiagnosisList(){
        String diagnoses = diagnosesList.stream().map(x -> x.getName()).collect( Collectors.joining( "," ) );
//        StringBuilder returnedString = list.stream().map(e -> e.toString());
        return diagnoses;
    }

    @Column(name = "date_created")
    private LocalDateTime creationDate;

}

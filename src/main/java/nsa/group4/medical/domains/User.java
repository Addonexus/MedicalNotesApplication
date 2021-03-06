package nsa.group4.medical.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Categories> categoriesList;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Diagnosis> diagnosisList;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<CaseModel> casesList;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Notifications> notificationsList;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ward> wardList;
    //private Role role;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Role> roles;

}


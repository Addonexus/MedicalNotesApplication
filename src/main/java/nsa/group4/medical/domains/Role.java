package nsa.group4.medical.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userroleid;

    @Column(name = "userid")
    private Long userid;

    @Column(name = "role")
    private String role;

//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;

}

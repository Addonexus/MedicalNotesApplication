package nsa.group4.medical.domains;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name="userid")
//    private Long userId;

    @Column(name="role")
    private String name;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = {@JoinColumn(name="role_id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id")})
//    @JsonManagedReference
//    private Set<User> users;

}
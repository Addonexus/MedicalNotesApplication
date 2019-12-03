package nsa.group4.medical.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="auth_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_user_id")
    private Long id;

    @Column(name = "first_name")
    @NotNull
    @NotEmpty
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    @NotEmpty
    private String lastName;

    @Column(name = "email")
    @NotNull
    @NotEmpty
    private String email;

    @Column(name = "password")
    @NotNull
    @NotEmpty
    private String password;

    @Column(name = "status")
    @NotNull
    @NotEmpty
    private String status;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "auth_user_role",
            joinColumns = @JoinColumn(name = "auth_user_id"),
            inverseJoinColumns = @JoinColumn(name = "auth_role_id")
    )private Set<Role> roles;


}

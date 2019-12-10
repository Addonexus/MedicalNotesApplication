//package nsa.group4.medical.data;
//
//import nsa.group4.medical.domains.Role;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface RoleRepositoryJPA extends JpaRepository<Role, Long> {
//
//   @Query("select a.role from Role a, User b where b.username=?1 and a.userid=b.id")
//    public List<String> findRoleByUsername(String username);
//}

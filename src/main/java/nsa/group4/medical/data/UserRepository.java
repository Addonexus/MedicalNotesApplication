package nsa.group4.medical.data;


import nsa.group4.medical.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);

  @Modifying
  @Query(value = "UPDATE user SET ward_id = ?1 WHERE id = ?2", nativeQuery = true)
  void setWardIdForUser(Long wardId, Long id);
}

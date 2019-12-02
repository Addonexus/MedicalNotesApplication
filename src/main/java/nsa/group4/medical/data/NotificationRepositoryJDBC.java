package nsa.group4.medical.data;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class NotificationRepositoryJDBC {

    private JdbcOperations jdbcOperations;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public NotificationRepositoryJDBC(JdbcOperations aJdbcOperations) {jdbcOperations = aJdbcOperations;}


}

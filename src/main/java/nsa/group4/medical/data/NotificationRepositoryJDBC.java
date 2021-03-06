package nsa.group4.medical.data;


import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.Diagnosis;
import nsa.group4.medical.domains.Notifications;
import nsa.group4.medical.domains.User;
import nsa.group4.medical.domains.rowmappers.NotificationRowmapper;
import nsa.group4.medical.service.implementations.NotificationRepositoryInterface;
import nsa.group4.medical.service.events.NotificationAdded;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.management.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Repository
public class NotificationRepositoryJDBC implements NotificationRepositoryInterface {

    private JdbcOperations jdbcOperations;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public NotificationRepositoryJDBC(JdbcOperations aJdbcOperations) {jdbcOperations = aJdbcOperations;}



    @Override
    public Notifications findByDiagnosisLink(Diagnosis diagnosis) {
        return null;
    }

    @Override
    public Notifications save(Notifications notifications) {
        saveNotificationDetails(notifications.getId(), notifications.getContent());;
        return notifications;
    }

    @Override
    public List<Notifications> findAll() {
        return getAllNotifications();
    }

    @Override
    public List<Notifications> findByUser(User user) {
        return null;
    }

    private Long saveNotificationDetails(Long id, String content){
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcOperations.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps =
                                con.prepareStatement("INSERT INTO notifications(id, content)" +
                                        "VALUES(?, ?)"
                                , Statement.RETURN_GENERATED_KEYS);

                        ps.setLong(1, id);
                        ps.setString(2, content);
                        return ps;
                    }
                },
                holder);
        return holder.getKey().longValue();
    }

    public List<Notifications> getAllNotifications(){
        return jdbcTemplate.query("SELECT * FROM notifications",
                new NotificationRowmapper());
    }
}

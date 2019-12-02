package nsa.group4.medical.domains.rowmappers;

import nsa.group4.medical.domains.Notification;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowmapper implements RowMapper<Notification> {

    @Override
    public Notification mapRow(ResultSet resultSet, int rowNun) throws SQLException {
        Notification notification = new Notification();
        notification.setId(resultSet.getLong("id"));
        notification.setContent(resultSet.getString("content"));
        
        return notification;
    }
}

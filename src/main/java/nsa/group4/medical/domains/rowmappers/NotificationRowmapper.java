package nsa.group4.medical.domains.rowmappers;

import nsa.group4.medical.domains.Notifications;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationRowmapper implements RowMapper<Notifications> {

    @Override
    public Notifications mapRow(ResultSet resultSet, int rowNun) throws SQLException {
        Notifications notifications = new Notifications();
        notifications.setId(resultSet.getLong("id"));
        notifications.setContent(resultSet.getString("content"));
        
        return notifications;
    }
}

package nsa.group4.medical.domains.rowmappers;

import nsa.group4.medical.domains.Ward;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WardRowmapper implements RowMapper<Ward> {
    @Override
    public Ward mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Ward ward = new Ward();
        ward.setId(resultSet.getLong("id"));
        ward.setName(resultSet.getString("name"));

        return ward;
    }
}

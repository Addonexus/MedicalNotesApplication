package nsa.group4.medical.data;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.DiagnosisInformation;
import nsa.group4.medical.domains.rowmappers.DiagnosisInformationRowmapper;
import nsa.group4.medical.domains.rowmappers.WardRowmapper;
import nsa.group4.medical.service.events.WardAdded;
import nsa.group4.medical.service.implementations.WardJDBCRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class WardRepositoryJDBC implements WardJDBCRepositoryInterface {

    private JdbcOperations jdbcOperations;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public WardRepositoryJDBC (JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public void saveWard(WardAdded wardAdded){
        saveWardDetails(wardAdded.getId(), wardAdded.getName());
    }

    private Long saveWardDetails(Long id, String name){
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbcOperations.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("INSERT INTO ward(id, name)" +
                                                "VALUES(?, ?)"
                                        , new String[] {"id"});
                        ps.setLong(1, id);
                        ps.setString(2, name);
                        return ps;
                    }
                },
                holder);
        return holder.getKey().longValue();
    }

    public List<DiagnosisInformation> getAllWards(){
        return jdbcTemplate.query("SELECT * FROM ward"
                , new WardRowmapper());
    }
}

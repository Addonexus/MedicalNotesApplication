package nsa.group4.medical.data;

import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.service.DiagnosisInformationRepositoryInterface;
import nsa.group4.medical.service.events.DiagnosisInformationAdded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Repository
public class DiagnosisInformationRepositoryJDBC implements DiagnosisInformationRepositoryInterface {

    private JdbcOperations jdbc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static final Logger LOG = LoggerFactory.getLogger(DiagnosisInformationRepositoryJDBC.class);

    public DiagnosisInformationRepositoryJDBC(JdbcOperations aJdbc) {
        jdbc = aJdbc;
    }

    @Override
    public void saveDiagnosisInformation(DiagnosisInformationAdded diagnosisInformationEvent) {
        saveDiagnosisInformationDetails(diagnosisInformationEvent.getDonationId(), diagnosisInformationEvent.getKey(), diagnosisInformationEvent.getValue());
    }

    private Long saveDiagnosisInformationDetails(Long diagnosisId, String key, String value) {
        GeneratedKeyHolder holder = new GeneratedKeyHolder();

        jdbc.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("INSERT INTO diagnosis_info(diagnosis_id, key, value)" +
                                                "VALUES(?, ?, ?)"
                                        , Statement.RETURN_GENERATED_KEYS);

                        ps.setLong(1, diagnosisId);
                        ps.setString(2, key);
                        ps.setString(3, value);
                        return ps;
                    }
                },
                holder);
        return holder.getKey().longValue();
    }
}

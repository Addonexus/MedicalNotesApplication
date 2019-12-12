package nsa.group4.medical.data;


import lombok.extern.slf4j.Slf4j;
import nsa.group4.medical.domains.DiagnosisInformation;
import nsa.group4.medical.domains.FreehandNotes;
import nsa.group4.medical.domains.rowmappers.DiagnosisInformationRowmapper;
import nsa.group4.medical.domains.rowmappers.FreehandNotesRowmapper;
import nsa.group4.medical.service.events.FreehandNotesAdded;
import nsa.group4.medical.service.implementations.DiagnosisInformationRepositoryInterface;
import nsa.group4.medical.service.events.DiagnosisInformationAdded;
import nsa.group4.medical.service.implementations.FreehandNotesRepositoryInterface;
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
import java.util.List;

@Slf4j
@Repository
public class FreehandNotesRepoJDBC implements FreehandNotesRepositoryInterface {


    private JdbcOperations jdbc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public FreehandNotesRepoJDBC(JdbcOperations newJdbc){
        jdbc = newJdbc;
    }

    static final Logger LOG = LoggerFactory.getLogger(FreehandNotesRepoJDBC.class);

    @Override
    public void save(FreehandNotesAdded freehandNotesAdded){
        saveFreehandNotesDetails(freehandNotesAdded.getDiagnosisId(),
                freehandNotesAdded.getField());
    }

private Long saveFreehandNotesDetails(Long diagnosisId,
        String field){
        GeneratedKeyHolder generatedKeyHolder =
                new GeneratedKeyHolder();


        jdbc.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps =
                                con.prepareStatement(
                                        "INSERT INTO freehand_notes(diagnosis_id,field)"
                                        + "VALUES(?,?)",
                                        new String [] {"diagnosis_id"});
                        ps.setLong(1, diagnosisId);
                        ps.setString(2, field);
                        return ps;

                    }
                }, generatedKeyHolder);
        return generatedKeyHolder.getKey().longValue();
}
    public List<FreehandNotes> getFreehandNotesByDiagnosisId(Long index){
        return jdbcTemplate.query("SELECT * FROM freehand_notes WHERE diagnosis_id = ?",
                preparedStatement ->
                {
                    preparedStatement.setLong(1, index);
                }, new FreehandNotesRowmapper());
    }

    public List<FreehandNotes> getAllFreehandNotes(){
        return jdbcTemplate.query("SELECT * FROM freehand_notes",
                new FreehandNotesRowmapper());
    }

}

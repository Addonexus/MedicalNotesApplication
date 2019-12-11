package nsa.group4.medical.domains.rowmappers;

import nsa.group4.medical.domains.DiagnosisInformation;
import nsa.group4.medical.domains.FreehandNotes;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FreehandNotesRowmapper implements RowMapper<FreehandNotes> {
    @Override
    public FreehandNotes mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        FreehandNotes freehandNotes = new FreehandNotes();
        freehandNotes.setId(resultSet.getLong("id"));
        freehandNotes.setField(resultSet.getString("field"));
        
        return freehandNotes;
    }

}

package nsa.group4.medical.domains.rowmappers;

import nsa.group4.medical.domains.DiagnosisInformation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiagnosisInformationRowmapper implements RowMapper<DiagnosisInformation> {

    @Override
    public DiagnosisInformation mapRow(ResultSet resultSet, int rowNum) throws SQLException{
        DiagnosisInformation diagnosisInformation = new DiagnosisInformation();
        diagnosisInformation.setId(resultSet.getLong("id"));
        diagnosisInformation.setField(resultSet.getString("field"));
        diagnosisInformation.setValue(resultSet.getString("value"));

        return diagnosisInformation;
    }
}

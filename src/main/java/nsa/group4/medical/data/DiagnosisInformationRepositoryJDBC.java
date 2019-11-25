package nsa.group4.medical.data;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class DiagnosisInformationRepositoryJDBC {

    private JdbcOperations jdbc;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static final Logger LOG = LoggerFactory.getLogger(DiagnosisInformationRepositoryJDBC.class);

    public DiagnosisInformationRepositoryJDBC(JdbcOperations aJdbc) {jdbc = aJdbc;}

    @Override
    public void saveDiagnosisInformation()
}

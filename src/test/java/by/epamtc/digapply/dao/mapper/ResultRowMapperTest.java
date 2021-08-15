package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.entity.Result;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ResultRowMapperTest {
    private static final long EXPECTED_LONG = 1L;
    private static final int EXPECTED_INT = 1;
    private static final String EXPECTED_STRING = "test";

    @Test
    void mapTest() throws SQLException {
        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getLong(Column.RESULT_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getLong(Column.APPLICATION_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getLong(Column.SUBJECT_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getInt(Column.SCORE)).thenReturn(EXPECTED_INT);
        Mockito.when(rsMock.getString(Column.CERTIFICATE_ID)).thenReturn(EXPECTED_STRING);

        Result expected = new Result();
        expected.setResultId(EXPECTED_LONG);
        expected.setApplicationId(EXPECTED_LONG);
        expected.setSubjectId(EXPECTED_LONG);
        expected.setScore(EXPECTED_INT);
        expected.setCertificateId(EXPECTED_STRING);

        ResultRowMapper mapper = new ResultRowMapper();
        Result actual = mapper.map(rsMock);

        assertEquals(expected, actual);
    }
}
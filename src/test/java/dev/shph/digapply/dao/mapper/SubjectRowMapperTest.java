package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.Subject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SubjectRowMapperTest {
    private static final long EXPECTED_LONG = 1L;
    private static final String EXPECTED_STRING = "test";

    @Test
    void mapTest() throws SQLException {
        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getLong(Column.SUBJECT_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getString(Column.SUBJECT_NAME)).thenReturn(EXPECTED_STRING);

        Subject expected = new Subject(EXPECTED_LONG, EXPECTED_STRING);

        SubjectRowMapper mapper = new SubjectRowMapper();
        Subject actual = mapper.map(rsMock);

        assertEquals(expected, actual);
    }
}
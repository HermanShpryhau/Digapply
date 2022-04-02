package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.Faculty;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class FacultyRowMapperTest {
    private static final long EXPECTED_LONG = 1L;
    private static final int EXPECTED_INT = 1;
    private static final String EXPECTED_STRING = "test";
    private static final boolean EXPECTED_BOOLEAN = true;

    @Test
    void mapTest() throws SQLException {
        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getLong(Column.FACULTY_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getString(Column.FACULTY_NAME)).thenReturn(EXPECTED_STRING);
        Mockito.when(rsMock.getString(Column.FACULTY_SHORT_DESCRIPTION)).thenReturn(EXPECTED_STRING);
        Mockito.when(rsMock.getString(Column.FACULTY_DESCRIPTION)).thenReturn(EXPECTED_STRING);
        Mockito.when(rsMock.getInt(Column.FACULTY_PLACES)).thenReturn(EXPECTED_INT);
        Mockito.when(rsMock.getBoolean(Column.IS_APPLICATION_CLOSED)).thenReturn(EXPECTED_BOOLEAN);

        FacultyRowMapper mapper = new FacultyRowMapper();
        Faculty actual = mapper.map(rsMock);

        Faculty expected = new Faculty();
        expected.setFacultyId(EXPECTED_LONG);
        expected.setFacultyName(EXPECTED_STRING);
        expected.setFacultyShortDescription(EXPECTED_STRING);
        expected.setFacultyDescription(EXPECTED_STRING);
        expected.setPlaces(EXPECTED_INT);
        expected.setApplicationClosed(EXPECTED_BOOLEAN);

        assertEquals(expected, actual);
    }
}
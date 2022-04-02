package dev.shph.digapply.dao.mapper;

import dev.shph.digapply.entity.Application;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationRowMapperTest {
    private static final long EXPECTED_LONG = 1L;
    private static final Timestamp EXPECTED_TIMESTAMP = new Timestamp(1);
    private static final boolean EXPECTED_BOOLEAN = true;

    @Test
    void mapTest() throws SQLException {
        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getLong(Column.APPLICATION_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getLong(Column.USER_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getLong(Column.FACULTY_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getTimestamp(Column.APPLY_DATE)).thenReturn(EXPECTED_TIMESTAMP);
        Mockito.when(rsMock.getBoolean(Column.APPROVED)).thenReturn(EXPECTED_BOOLEAN);
        Mockito.when(rsMock.getTimestamp(Column.APPROVE_DATE)).thenReturn(EXPECTED_TIMESTAMP);

        Application expected = new Application();
        expected.setApplicationId(EXPECTED_LONG);
        expected.setUserId(EXPECTED_LONG);
        expected.setFacultyId(EXPECTED_LONG);
        expected.setApplyDate(EXPECTED_TIMESTAMP);
        expected.setApproved(EXPECTED_BOOLEAN);
        expected.setApproveDate(EXPECTED_TIMESTAMP);

        ApplicationRowMapper applicationRowMapper = new ApplicationRowMapper();
        Application actual = applicationRowMapper.map(rsMock);
        assertEquals(expected, actual);
    }
}
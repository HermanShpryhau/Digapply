package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserRowMapperTest {
    private static final long EXPECTED_LONG = 1L;
    private static final String EXPECTED_STRING = "test";
    private static final UserRole EXPECTED_ROLE = UserRole.ADMIN;

    @Test
    void mapTest() throws SQLException {
        ResultSet rsMock = Mockito.mock(ResultSet.class);
        Mockito.when(rsMock.getLong(Column.USER_ID)).thenReturn(EXPECTED_LONG);
        Mockito.when(rsMock.getString(Column.USER_EMAIL)).thenReturn(EXPECTED_STRING);
        Mockito.when(rsMock.getString(Column.USER_PASSWORD)).thenReturn(EXPECTED_STRING);
        Mockito.when(rsMock.getString(Column.USER_NAME)).thenReturn(EXPECTED_STRING);
        Mockito.when(rsMock.getString(Column.USER_SURNAME)).thenReturn(EXPECTED_STRING);
        Mockito.when(rsMock.getString(Column.ROLE_NAME)).thenReturn(EXPECTED_ROLE.name());
        Mockito.when(rsMock.getLong(Column.USER_ROLE)).thenReturn(EXPECTED_LONG);

        User expected = new User();
        expected.setUserId(EXPECTED_LONG);
        expected.setEmail(EXPECTED_STRING);
        expected.setPassword(EXPECTED_STRING);
        expected.setName(EXPECTED_STRING);
        expected.setSurname(EXPECTED_STRING);
        expected.setRole(EXPECTED_ROLE);
        expected.setRoleId(EXPECTED_LONG);

        UserRowMapper mapper = new UserRowMapper();
        User actual = mapper.map(rsMock);

        assertEquals(expected, actual);
    }
}
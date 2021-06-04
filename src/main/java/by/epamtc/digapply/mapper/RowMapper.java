package by.epamtc.digapply.mapper;

import by.epamtc.digapply.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T extends Identifiable> {

    T map(ResultSet resultSet) throws SQLException;

}

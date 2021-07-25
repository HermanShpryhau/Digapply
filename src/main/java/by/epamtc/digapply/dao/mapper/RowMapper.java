package by.epamtc.digapply.dao.mapper;

import by.epamtc.digapply.entity.Identifiable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface of an entity fields mapper
 * @param <T> Type of mapped entity
 */
@FunctionalInterface
public interface RowMapper<T extends Identifiable> {

    /**
     * Maps {@link ResultSet} row to entity.
     * @param resultSet {@link ResultSet} whose pointer is set to row data to be mapped on entity object.
     * @return Entity object with fields set from row data.
     */
    T map(ResultSet resultSet) throws SQLException;

}

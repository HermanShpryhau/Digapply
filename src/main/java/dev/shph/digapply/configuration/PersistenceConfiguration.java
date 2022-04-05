package dev.shph.digapply.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.shph.digapply.dao.Table;
import dev.shph.digapply.dao.mapper.Column;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class PersistenceConfiguration {

    public static final String APPLICATION_INSERT = "applicationInsert";
    public static final String FACULTY_INSERT = "facultyInsert";
    public static final String RESULT_INSERT = "resultInsert";
    public static final String SUBJECT_INSERT = "subjectInsert";
    public static final String USER_INSERT = "userInsert";

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/digapply-db");
        config.setUsername("jwd-user");
        config.setPassword("25242322232425");
        config.setMaximumPoolSize(5);
        config.setConnectionTestQuery("SELECT 1");
        config.setPoolName("springHikariCP");
        config.addDataSourceProperty("dataSource.cachePrepStmts", "true");
        config.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
        config.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Qualifier(APPLICATION_INSERT)
    public SimpleJdbcInsert applicationJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(Table.APPLICATION_TABLE)
                .usingGeneratedKeyColumns(Column.APPLICATION_ID);
    }

    @Bean
    @Qualifier(FACULTY_INSERT)
    public SimpleJdbcInsert facultyJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(Table.FACULTY_TABLE)
                .usingColumns(Column.USER_ID, Column.FACULTY_ID)
                .usingGeneratedKeyColumns(Column.FACULTY_ID);
    }

    @Bean
    @Qualifier(RESULT_INSERT)
    public SimpleJdbcInsert resultJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(Table.RESULT_TABLE)
                .usingGeneratedKeyColumns(Column.RESULT_ID);
    }

    @Bean
    @Qualifier(SUBJECT_INSERT)
    public SimpleJdbcInsert subjectJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(Table.SUBJECT_TABLE)
                .usingGeneratedKeyColumns(Column.SUBJECT_ID);
    }

    @Bean
    @Qualifier(USER_INSERT)
    public SimpleJdbcInsert userJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(Table.USER_TABLE)
                .usingGeneratedKeyColumns(Column.USER_ID);
    }
}

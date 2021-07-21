package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.dao.connection.ConnectionPool;
import by.epamtc.digapply.dao.connection.ConnectionPoolException;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.FacultyDao;
import by.epamtc.digapply.entity.Faculty;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FacultyDaoImplTest {
    FacultyDao facultyDao = new FacultyDaoImpl();

    @BeforeAll
    static void setUpConnectionPool() throws ConnectionPoolException {
        ConnectionPool.getInstance().initialize();
    }

    @AfterAll
    static void tearDown() throws ConnectionPoolException {
        ConnectionPool.getInstance().dispose();
    }

    @Test
    @Order(2)
    void findById() throws DaoException {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(3);
        faculty.setFacultyName("Star Fleet Science College");
        faculty.setFacultyShortDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.");
        faculty.setFacultyDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        faculty.setPlaces(20);
        faculty.setApplicationClosed(false);
        Faculty fromDB = facultyDao.findById(3);
        assertEquals(faculty, fromDB);
    }
}
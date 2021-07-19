package by.epamtc.digapply.dao.impl;

import by.epamtc.digapply.connection.ConnectionPool;
import by.epamtc.digapply.connection.ConnectionPoolException;
import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.FacultyDao;
import by.epamtc.digapply.entity.Faculty;
import org.junit.jupiter.api.*;

import javax.management.MXBean;

import java.util.Arrays;

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
//
//    @Test
//    @Order(1)
//    void save() throws DaoException {
//        Faculty faculty = new Faculty();
//        faculty.setFacultyName("test");
//        faculty.setFacultyShortDescription("test");
//        faculty.setFacultyDescription("test");
//        faculty.setPlaces(1);
//        facultyDao.save(faculty);
//    }

    @Test
    @Order(2)
    void findById() throws DaoException {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(2);
        faculty.setFacultyName("Star Fleet Engineering College");
        faculty.setFacultyShortDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.");
        faculty.setFacultyDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        faculty.setPlaces(25);
        Faculty fromDB = facultyDao.findById(2);
        assertEquals(faculty, fromDB);
    }

//    @Test
//    @Order(3)
//    void updateEntity() {
//
//    }

//    @Test
//    @Order(4)
//    void removeById() {
//    }
}
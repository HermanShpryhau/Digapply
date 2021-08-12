package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.connection.ConnectionPool;
import by.epamtc.digapply.dao.connection.ConnectionPoolException;
import by.epamtc.digapply.service.MailService;
import by.epamtc.digapply.service.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MailServiceImplTest {

    @BeforeAll
    static void setUp() throws ConnectionPoolException {
        ConnectionPool.getInstance().initialize();
    }

    @AfterAll
    static void tearDow() throws ConnectionPoolException {
        ConnectionPool.getInstance().dispose();
    }

    @Test
    void sendApplicationApprovalMessageTest() throws ServiceException {
        MailService mailService = new MailServiceImpl();
        mailService.sendApplicationApprovalMessage(33, "ru");
        assertTrue(true);
    }

    @Test
    void sendApplicationAcceptedMessageTest() throws ServiceException {
        MailService mailService = new MailServiceImpl();
        mailService.sendApplicationAcceptedMessage(33, "ru");
        assertTrue(true);
    }
}
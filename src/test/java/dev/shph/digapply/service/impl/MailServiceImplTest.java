package dev.shph.digapply.service.impl;

import dev.shph.digapply.dao.connection.ConnectionPool;
import dev.shph.digapply.dao.connection.ConnectionPoolException;
import dev.shph.digapply.service.MailService;
import dev.shph.digapply.service.ServiceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

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
    @Disabled
    void sendApplicationApprovalMessageTest() throws ServiceException {
        MailService mailService = new MailServiceImpl();
        mailService.sendApplicationApprovalMessage(33, "ru");
        assertTrue(true);
    }

    @Test
    @Disabled
    void sendApplicationAcceptedMessageTest() throws ServiceException {
        MailService mailService = new MailServiceImpl();
        mailService.sendApplicationAcceptedMessage(33, "ru");
        assertTrue(true);
    }
}
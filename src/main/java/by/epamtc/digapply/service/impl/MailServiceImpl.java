package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.*;
import by.epamtc.digapply.entity.Application;
import by.epamtc.digapply.entity.Faculty;
import by.epamtc.digapply.entity.User;
import by.epamtc.digapply.service.MailService;
import by.epamtc.digapply.service.MailSessionCreator;
import by.epamtc.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger logger = LogManager.getLogger();
    private static final String MAIL_CONFIG_PROPERTIES_PATH = "mail.properties";
    private static final String MAIL_MESSAGE_BUNDLE = "mailcontent";
    private static final String MESSAGE_CONTENT_TYPE = "text/plain; charset=UTF-8";
    private static final String SUBJECT_ENCODING = "UTF-8";
    private static final String APPROVE_MESSAGE_SUBJECT = "approve.subject";
    private static final String APPROVE_MESSAGE_CONTENT = "approve.content";
    private static final String ACCEPTED_MESSAGE_SUBJECT = "accepted.subject";
    private static final String ACCEPTED_MESSAGE_CONTENT = "accepted.content";

    @Autowired
    private FacultyDao facultyDao;
    @Autowired
    private ApplicationDao applicationDao;

    @Override
    public void sendApplicationApprovalMessage(long applicationId, String locale) throws ServiceException {
        Properties mailProperties = loadMailProperties();
        MimeMessage message = createMessage(mailProperties);
        ResourceBundle mailBundle = getResourceBundle(locale);
        String recipientEmail = retrieveApplicantEmail(applicationId);
        String subject = getMailProperty(mailBundle, APPROVE_MESSAGE_SUBJECT);
        String content = formatContentString(applicationId, getMailProperty(mailBundle, APPROVE_MESSAGE_CONTENT));
        sendMessage(message, subject, content, recipientEmail);
    }

    @Override
    public void sendApplicationAcceptedMessage(long applicationId, String locale) throws ServiceException {
        Properties mailProperties = loadMailProperties();
        MimeMessage message = createMessage(mailProperties);
        ResourceBundle mailBundle = getResourceBundle(locale);
        String recipientEmail = retrieveApplicantEmail(applicationId);
        String subject = getMailProperty(mailBundle, ACCEPTED_MESSAGE_SUBJECT);
        String content = formatContentString(applicationId, getMailProperty(mailBundle, ACCEPTED_MESSAGE_CONTENT));
        sendMessage(message, subject, content, recipientEmail);
    }

    private Properties loadMailProperties() throws ServiceException {
        Properties mailProperties = new Properties();
        InputStream fileInputStream = getClass().getClassLoader().getResourceAsStream(MAIL_CONFIG_PROPERTIES_PATH);
        if (fileInputStream == null) {
            logger.error("File {} doesn't exist", MAIL_CONFIG_PROPERTIES_PATH);
            throw new ServiceException("File " + MAIL_CONFIG_PROPERTIES_PATH + " doesn't exist");
        }

        try {
            mailProperties.load(fileInputStream);
        } catch (IOException e) {
            logger.error("Unable to read mail properties. {}", e.getMessage());
            throw new ServiceException("Unable to read mail properties.", e);
        }
        return mailProperties;
    }

    private MimeMessage createMessage(Properties mailProperties) throws ServiceException {
        MailSessionCreator creator = new MailSessionCreator(mailProperties);
        Session session = creator.createSession();
        return new MimeMessage(session);
    }

    private ResourceBundle getResourceBundle(String locale) {
        Locale messageLocale = new Locale(locale);
        return ResourceBundle.getBundle(MAIL_MESSAGE_BUNDLE, messageLocale);
    }

    private String getMailProperty(ResourceBundle bundle, String key) throws ServiceException {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            logger.error("Property not specified. {}", e.getMessage());
            throw new ServiceException("Property not specified.", e);
        }
    }

    private String formatContentString(long applicationId, String content) throws ServiceException {
        try {
            Application application = applicationDao.findById(applicationId);
            Faculty faculty = facultyDao.findById(application.getFacultyId());
            return String.format(content, faculty.getFacultyName());
        } catch (DaoException e) {
            logger.error("Unable to fetch faculty for application by id. {}", e.getMessage());
            throw new ServiceException("Unable to fetch faculty for application by id.", e);
        }
    }

    private String retrieveApplicantEmail(long applicationId) throws ServiceException {
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        Application application;
        try {
            application = applicationDao.findById(applicationId);
            User recipient = userDao.findById(application.getUserId());
            return recipient.getEmail();
        } catch (DaoException e) {
            logger.error("Unable to fetch applicant's email. {}", e.getMessage());
            throw new ServiceException("Unable to fetch applicant's email.", e);
        }
    }

    private void sendMessage(MimeMessage message, String subject, String content, String recipientEmail) throws ServiceException {
        try {
            message.setSubject(subject, SUBJECT_ENCODING);
            message.setContent(content, MESSAGE_CONTENT_TYPE);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            Transport.send(message);
        } catch (MessagingException e) {
            logger.error("Unable to send message. {}", e.getMessage());
            throw new ServiceException("Unable to send message.", e);
        }
    }
}

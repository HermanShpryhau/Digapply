package by.epamtc.digapply.service;

public interface MailService {

    /**
     * Send email informing user that his application was approved
     * @param applicationId ID of application.
     */
    void sendApplicationApprovalMessage(long applicationId, String locale) throws ServiceException;

    /**
     * Send email informaing user that his application was accepted.
     * @param applicationId ID of application.
     */
    void sendApplicationAcceptedMessage(long applicationId, String locale) throws ServiceException;

}

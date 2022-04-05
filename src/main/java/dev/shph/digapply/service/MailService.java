package dev.shph.digapply.service;

public interface MailService {

    /**
     * Sends email informing user that his application was approved
     * @param applicationId ID of application.
     */
    void sendApplicationApprovalMessage(long applicationId, String locale) throws ServiceException;

    /**
     * Sends email informing user that his application was accepted.
     * @param applicationId ID of application.
     */
    void sendApplicationAcceptedMessage(long applicationId, String locale) throws ServiceException;

}

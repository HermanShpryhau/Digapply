package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.Application;

import java.util.Map;

public interface ApplicationService {

    boolean saveApplication(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException;

    boolean hasApplication(long userId) throws ServiceException;

    Application retrieveApplicationByUserId(long userId) throws ServiceException;

    int calculateTotalScore(long applicationId) throws ServiceException;

    boolean cancelApplication(long userId) throws ServiceException;
}

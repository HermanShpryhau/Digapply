package by.epamtc.digapply.service;

import java.util.Map;

public interface ApplicationService {

    boolean saveApplication(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) throws ServiceException;

}

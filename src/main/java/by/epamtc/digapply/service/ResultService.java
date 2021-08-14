package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.dto.ResultDto;

import java.util.List;

public interface ResultService {

    /**
     * Retrieves list of subject DTOs for given application.
     * @param applicationId ID of application.
     * @return List of subject DTOs.
     */
    List<ResultDto> retrieveResultsForApplication(long applicationId) throws ServiceException;

}

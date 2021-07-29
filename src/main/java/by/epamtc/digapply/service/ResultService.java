package by.epamtc.digapply.service;

import by.epamtc.digapply.entity.dto.ResultDto;

import java.util.List;

public interface ResultService {

    List<ResultDto> retrieveResultsForApplication(long facultyId) throws ServiceException;

}

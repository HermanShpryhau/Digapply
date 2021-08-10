package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.dao.DaoFactory;
import by.epamtc.digapply.dao.ResultDao;
import by.epamtc.digapply.dao.SubjectDao;
import by.epamtc.digapply.entity.Result;
import by.epamtc.digapply.entity.dto.ResultDto;
import by.epamtc.digapply.service.ResultService;
import by.epamtc.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ResultServiceImpl implements ResultService {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<ResultDto> retrieveResultsForApplication(long applicationId) throws ServiceException {
        List<ResultDto> dtos = new ArrayList<>();
        ResultDao resultDao = DaoFactory.getInstance().getResultDao();
        try {
            List<Result> results = resultDao.findByApplicationId(applicationId);
            for (Result result : results) {
                dtos.add(buildResultDto(result));
            }
        } catch (DaoException e) {
            logger.error("Unable to fetch results for application.", e);
            throw new ServiceException("Unable to fetch results for application.", e);
        }
        return dtos;
    }

    private ResultDto buildResultDto(Result result) throws DaoException {
        ResultDto dto = new ResultDto();
        dto.setResultId(result.getId());
        dto.setApplicationId(dto.getApplicationId());
        long subjectId = result.getSubjectId();
        dto.setSubjectId(subjectId);
        dto.setSubjectName(getSubjectName(subjectId));
        dto.setScore(result.getScore());
        dto.setCertificateId(result.getCertificateId());
        return dto;
    }

    private String getSubjectName(long subjectId) throws DaoException {
        SubjectDao subjectDao = DaoFactory.getInstance().getSubjectDao();
        return subjectDao.findById(subjectId).getSubjectName();
    }
}

package dev.shph.digapply.service.impl;

import dev.shph.digapply.dao.DaoException;
import dev.shph.digapply.dao.ResultDao;
import dev.shph.digapply.dao.SubjectDao;
import dev.shph.digapply.entity.Result;
import dev.shph.digapply.entity.dto.ResultDto;
import dev.shph.digapply.service.ResultService;
import dev.shph.digapply.service.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ResultDao resultDao;
    @Autowired
    private SubjectDao subjectDao;

    @Override
    public List<ResultDto> retrieveResultsForApplication(long applicationId) throws ServiceException {
        List<ResultDto> dtos = new ArrayList<>();
        try {
            List<Result> results = resultDao.findByApplicationId(applicationId);
            for (Result result : results) {
                dtos.add(buildResultDto(result));
            }
        } catch (DaoException e) {
            logger.error("Unable to fetch results for application. {}", e.getMessage());
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
        return subjectDao.findById(subjectId).getSubjectName();
    }
}

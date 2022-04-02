package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.dao.DaoException;
import by.epamtc.digapply.model.dto.ApplicationDto;
import by.epamtc.digapply.model.dto.ResultDto;
import by.epamtc.digapply.model.Application;
import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.model.Result;
import by.epamtc.digapply.model.Subject;
import by.epamtc.digapply.model.User;
import by.epamtc.digapply.repository.ApplicationRepository;
import by.epamtc.digapply.repository.FacultyRepository;
import by.epamtc.digapply.repository.ResultRepository;
import by.epamtc.digapply.repository.SubjectRepository;
import by.epamtc.digapply.repository.UserRepository;
import by.epamtc.digapply.service.*;
import by.epamtc.digapply.service.validation.ApplicationFormDataValidator;
import by.epamtc.digapply.service.validation.ValidatorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private static final String SCORE_PREFIX = "sid-";
    private static final String CERTIFICATE_ID_PREFIX = "cid-";

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<ApplicationDto> convertToDto(List<Application> applications) {
        List<ApplicationDto> dtos = new ArrayList<>();
        for (Application application : applications) {
            dtos.add(buildApplicationDto(application));
        }
        return dtos;
    }

    @Override
    public boolean hasApplication(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.filter(value -> applicationRepository.findByUser(value).isPresent()).isPresent();
    }

    @Override
    public Application retrieveApplicationByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.flatMap(value -> applicationRepository.findByUser(value)).orElse(null);
    }

    @Override
    public List<ApplicationDto> retrieveApplicationsByFaculty(long facultyId) {
        List<ApplicationDto> dtos = new ArrayList<>();
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);
        if (faculty.isPresent()) {
            List<Application> applications = applicationRepository.findByFaculty(faculty.get());
            for (Application application : applications) {
                dtos.add(buildApplicationDto(application));
            }
        }
        return dtos;
    }

    @Override
    public List<ApplicationDto> retrieveAcceptedApplicationsByFaculty(long facultyId) {
        List<ApplicationDto> dtos = new ArrayList<>();
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);
        if (faculty.isPresent()) {
            List<Application> applications = applicationRepository.findByFaculty(faculty.get());
            dtos = applications.stream()
                    .filter(Application::getAccepted)
                    .map(this::buildApplicationDto)
                    .collect(Collectors.toList());
        }
        return dtos;
    }

    @Override
    public List<ApplicationDto> retrieveAllApplicationsDto() {
        return applicationRepository.findAll().stream()
                .map(this::buildApplicationDto)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDto retrieveApplicationDtoById(long id) {
        Application application = applicationRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return buildApplicationDto(application);
    }

    private ApplicationDto buildApplicationDto(Application application) {
        ApplicationDto dto = new ApplicationDto();
        dto.setApplicationId(application.getId());
        dto.setApplicantId(application.getUser().getId());
        dto.setApplicantName(application.getUser().getName());
        dto.setFacultyName(application.getFaculty().getFacultyName());
        List<ResultDto> results = getResultsForApplication(application.getId());
        dto.setResults(application.getResults());
        dto.setTotalScore(calculateTotalScoreFromDto(results));
        dto.setApplyDate(application.getApplyDate());
        dto.setApproved(application.getApproved());
        dto.setApproveDate(application.getApproveDate());
        return dto;
    }

    private List<ResultDto> getResultsForApplication(long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(NoSuchElementException::new);
        return application.getResults().stream().map(this::buildResultDto).collect(Collectors.toList());
    }

    private ResultDto buildResultDto(Result result) {
        ResultDto dto = new ResultDto();
        dto.setResultId(result.getId());
        dto.setApplicationId(dto.getApplicationId());
        dto.setSubjectId(result.getSubject().getId());
        dto.setSubjectName(result.getSubject().getSubjectName());
        dto.setScore(result.getScore());
        dto.setCertificateId(result.getCertificateId());
        return dto;
    }

    private int calculateTotalScoreFromDto(List<ResultDto> results) {
        return results.stream().mapToInt(ResultDto::getScore).sum();
    }

    @Override
    public int calculateTotalScore(Application application) {
        return application.getResults().stream().map(Result::getScore).reduce(0, Integer::sum);
    }

    @Override
    public boolean cancelApplication(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            Optional<Application> application = applicationRepository.findByUser(user.get());
            if (application.isPresent()) {
                applicationRepository.delete(application.get());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean approveApplication(long applicationId) {
        Optional<Application> application = applicationRepository.findById(applicationId);
        if (application.isPresent()) {
            application.get().setApproved(true);
            applicationRepository.save(application.get());
            return true;
        }
        return false;
    }

    @Override
    public boolean saveApplication(long userId, long facultyId, Map<String, String> scores, Map<String, String> certificateIds) {
        ApplicationFormDataValidator validator = ValidatorFactory.getInstance().getApplicationFormDataValidator();
        if (!validator.validate(userId, facultyId, scores, certificateIds)) {
            return false;
        }

        List<Result> results = buildResultsList(scores, certificateIds);
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);
        Optional<User> user = userRepository.findById(userId);
        if (faculty.isPresent() && user.isPresent()) {
            Application application = Application.builder()
                    .user(user.get())
                    .faculty(faculty.get())
                    .results(new HashSet<>(results))
                    .build();
            applicationRepository.save(application);
            return true;
        }

        return false;
    }

    @Override
    public boolean updateApplication(long applicationId, Map<String, String> scores, Map<String, String> certificateIds) {
        ApplicationFormDataValidator validator = ValidatorFactory.getInstance().getApplicationFormDataValidator();
        if (!validator.validateScoreMaps(scores, certificateIds)) {
            return false;
        }

        List<Result> results = buildResultsList(scores, certificateIds);
        Optional<Application> application = applicationRepository.findById(applicationId);
        if (application.isPresent()) {
            application.get().setResults(new HashSet<>(results));
            applicationRepository.save(application.get());
            return true;
        }
        return false;
    }

    private Application buildApplication(long userId, long facultyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(NoSuchElementException::new);
        return Application.builder()
                .user(user)
                .faculty(faculty)
                .build();
    }

    private List<Result> buildResultsList(Map<String, String> scores, Map<String, String> certificateIds) {
        Map<Long, String> scoresBySubject = buildIdBasedMap(scores, SCORE_PREFIX);
        Map<Long, String> certificateIdsBySubject = buildIdBasedMap(certificateIds, CERTIFICATE_ID_PREFIX);
        List<Result> resultList = new ArrayList<>();
        for (Map.Entry<Long, String> score : scoresBySubject.entrySet()) {
            String certificateId = certificateIdsBySubject.get(score.getKey());
            if (certificateId == null) {
                resultList.clear();
                break;
            }
            int scoreValue = Integer.parseInt(score.getValue());
            Result result = buildResult(score.getKey(), scoreValue, certificateId);
            resultList.add(result);
        }
        return resultList;
    }

    private Result buildResult(long subjectId, int score, String certificateId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(NoSuchElementException::new);
        return Result.builder()
                .subject(subject)
                .score(score)
                .certificateId(certificateId)
                .build();
    }

    private Map<Long, String> buildIdBasedMap(Map<String, String> map, String keyPrefix) {
        Map<String, String> mapWithoutPrefixes = removePrefixFromKeys(map, keyPrefix);
        Map<Long, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : mapWithoutPrefixes.entrySet()) {
            long id = Long.parseLong(entry.getKey());
            result.put(id, entry.getValue());
        }
        return result;
    }

    private Map<String, String> removePrefixFromKeys(Map<String, String> map, String prefix) {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            key = key.replace(prefix, "");
            result.put(key, entry.getValue());
        }
        return result;
    }
}

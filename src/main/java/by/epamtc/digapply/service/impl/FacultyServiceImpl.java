package by.epamtc.digapply.service.impl;

import by.epamtc.digapply.model.Application;
import by.epamtc.digapply.model.dto.ApplicationDto;
import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.model.Subject;
import by.epamtc.digapply.repository.ApplicationRepository;
import by.epamtc.digapply.repository.FacultyRepository;
import by.epamtc.digapply.repository.SubjectRepository;
import by.epamtc.digapply.service.ApplicationService;
import by.epamtc.digapply.service.FacultyService;
import by.epamtc.digapply.service.validation.EntityValidator;
import com.google.common.html.HtmlEscapers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private SubjectRepository subjectRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private EntityValidator<Faculty> facultyEntityValidator;

    @Override
    public Faculty saveFaculty(Faculty faculty, List<Long> subjectIds) {
        if (!isFacultyEntityValid(faculty)) {
            return null;
        }
        sanitizeDescription(faculty);
        Set<Subject> subjects = new HashSet<>();
        subjectIds.forEach(id -> subjectRepository.findById(id).ifPresent(subjects::add));
        return facultyRepository.save(faculty);
    }

    @Override
    public List<Faculty> retrieveBestFaculties() {
        return facultyRepository.findBestFaculties();
    }

    @Override
    public List<Faculty> retrieveFacultiesByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("facultyName").ascending());
        return facultyRepository.findAll(pageable).getContent();
    }

    @Override
    public long countPages(long elementsPerPage) {
        long rowsCount = facultyRepository.count();
        long leftover = rowsCount % elementsPerPage == 0 ? 0 : 1;
        return (rowsCount / elementsPerPage) + leftover;
    }

    @Override
    public Faculty retrieveFacultyById(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public List<Faculty> retrieveAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public List<Faculty> searchFaculties(String pattern, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("facultyName").ascending());
        return facultyRepository.findByFacultyNameLike(pattern, pageable).getContent();
    }

    @Override
    public long countPagesForSearchResult(String pattern, long elementsPerPage) {
        long rowsCount = facultyRepository.findByFacultyNameLike(pattern, Pageable.unpaged()).getTotalElements();
        long leftover = rowsCount % elementsPerPage == 0 ? 0 : 1;
        return (rowsCount / elementsPerPage) + leftover;
    }

    @Override
    public boolean updateFaculty(Faculty faculty) {
        if (isFacultyEntityValid(faculty)) {
            facultyRepository.save(faculty);
            return true;
        }
        return false;
    }

    private void sanitizeDescription(Faculty faculty) {
        String description = faculty.getFacultyDescription();
        description = escapeHtml(description);
        faculty.setFacultyDescription(description);
        String shortDescription = faculty.getFacultyShortDescription();
        shortDescription = escapeHtml(shortDescription);
        faculty.setFacultyShortDescription(shortDescription);
    }

    private String escapeHtml(String content) {
        content = HtmlEscapers.htmlEscaper().escape(content);
        content = content.replace("`", "\\`");
        return content;
    }

    @Override
    @Transactional
    public List<ApplicationDto> closeApplication(long facultyId) {
        Optional<Faculty> facultyOptional = facultyRepository.findById(facultyId);
        if (facultyOptional.isPresent()) {
            Faculty faculty = facultyOptional.get();
            faculty.setIsApplicationClosed(true);
            facultyRepository.save(faculty);
            List<Application> acceptedApplications = acceptApplications(faculty);
            return applicationService.convertToDto(acceptedApplications);
        }
        return null;
    }

    private Map<Application, Integer> buildApplicationScoresMap(List<Application> applications) {
        Map<Application, Integer> scores = new HashMap<>();
        for (Application application: applications) {
            scores.put(application, applicationService.calculateTotalScore(application));
        }
        return scores;
    }

    private List<Application> acceptApplications(Faculty faculty) {
        List<Application> applications = applicationRepository.findByFaculty(faculty);
        Map<Application, Integer> scores = buildApplicationScoresMap(applications);

        return scores.entrySet().stream()
                        .filter(entry -> entry.getKey().getApproved())
                        .sorted(Map.Entry.<Application, Integer>comparingByValue().reversed())
                        .sorted(Comparator.comparing(e -> e.getKey().getApplyDate()))
                        .limit(faculty.getPlaces())
                        .map(Map.Entry::getKey)
                        .map(application -> {
                            application.setAccepted(true);
                            return applicationRepository.save(application);
                        })
                        .collect(Collectors.toList());
    }

    @Override
    public boolean removeFacultyById(long facultyId) {
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);
        if (faculty.isPresent()) {
            facultyRepository.delete(faculty.get());
            return true;
        }
        return false;
    }

    private boolean isFacultyEntityValid(Faculty faculty) {
        return facultyEntityValidator.validate(faculty);
    }
}

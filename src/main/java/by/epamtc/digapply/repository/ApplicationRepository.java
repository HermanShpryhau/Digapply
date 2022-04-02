package by.epamtc.digapply.repository;

import by.epamtc.digapply.model.Application;
import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {
    List<Application> findAll();

    Optional<Application> findByUser(User user);

    List<Application> findByFaculty(Faculty faculty);
}
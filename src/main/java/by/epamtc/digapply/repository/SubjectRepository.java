package by.epamtc.digapply.repository;

import by.epamtc.digapply.model.Faculty;
import by.epamtc.digapply.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {
}
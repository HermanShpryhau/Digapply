package by.epamtc.digapply.repository;

import by.epamtc.digapply.model.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    @Query(value = "SELECT Faculties.faculty_id, faculty_name, faculty_short_description, faculty_description, places, is_application_closed, COUNT(A.application_id) AS count " +
            "FROM Faculties " +
            "JOIN Applications A on Faculties.faculty_id = A.faculty_id " +
            "GROUP BY Faculties.faculty_id " +
            "ORDER BY count DESC LIMIT 3",
            nativeQuery = true)
    List<Faculty> findBestFaculties();

    Page<Faculty> findByFacultyNameLike(String name, Pageable pageable);

    long findByFacultyNameLikeThanCount(String name);
}
package by.epamtc.digapply.repository;

import by.epamtc.digapply.model.Application;
import by.epamtc.digapply.model.Result;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResultRepository extends PagingAndSortingRepository<Result, Long> {
    List<Result> findByApplication(Application application);
}
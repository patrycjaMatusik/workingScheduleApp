package pl.pm.workscheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.pm.workscheduleapp.model.Worker;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findBySurnameAndNameIgnoreCase(String surname, String name);
    int deleteBySurnameAndNameIgnoreCase(String surname, String name);

    @Query("select w from Worker w where UPPER(w.surname) like upper(?1) or UPPER(w.name) like UPPER(?1)")
    Optional<List<Worker>> findBySurnameOrNameIgnoreCaseContaining(String phrase);
}

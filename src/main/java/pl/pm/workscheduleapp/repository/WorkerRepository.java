package pl.pm.workscheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pm.workscheduleapp.model.Worker;

import java.util.Optional;

public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Optional<Worker> findBySurnameAndNameIgnoreCase(String surname, String name);
}

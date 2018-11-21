package pl.pm.workscheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pm.workscheduleapp.model.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long> {

}

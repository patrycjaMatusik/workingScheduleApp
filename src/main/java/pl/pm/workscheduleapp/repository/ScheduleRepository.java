package pl.pm.workscheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.model.Worker;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}

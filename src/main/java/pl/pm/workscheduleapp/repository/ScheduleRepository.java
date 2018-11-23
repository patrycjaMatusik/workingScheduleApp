package pl.pm.workscheduleapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pm.workscheduleapp.model.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}

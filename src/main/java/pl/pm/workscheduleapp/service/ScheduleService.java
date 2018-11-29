package pl.pm.workscheduleapp.service;

import org.springframework.stereotype.Service;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.ScheduleRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void updateSchedule(Long id, String workDate, String startWorkingHour, String endWorkingHour){
        Optional<Schedule> byId = scheduleRepository.findById(id);
        Schedule scheduleToUpdate = byId.get();
        scheduleToUpdate.setWork_date(workDate);
        scheduleToUpdate.setStart_working_hour(startWorkingHour);
        scheduleToUpdate.setEnd_working_hour(endWorkingHour);
    }

    @Transactional
    public void deleteScheduleFromWorker(Schedule schedule, Worker worker){
        worker.getSchedules().remove(schedule);
    }

}

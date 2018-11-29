package pl.pm.workscheduleapp.service;

import org.springframework.stereotype.Service;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.repository.ScheduleRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public void updateSchedule(Long id, Date workDate, Date startWorkingHour, Date endWorkingHour){
        Optional<Schedule> byId = scheduleRepository.findById(id);
        Schedule scheduleToUpdate = byId.get();
        /*scheduleToUpdate.setWork_date2(workDate);
        scheduleToUpdate.setStart_working_hour2(startWorkingHour);
        scheduleToUpdate.setEnd_working_hour2(endWorkingHour);*/
    }

}

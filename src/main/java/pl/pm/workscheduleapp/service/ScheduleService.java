package pl.pm.workscheduleapp.service;

import org.springframework.stereotype.Service;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.ScheduleRepository;

import javax.transaction.Transactional;
import java.util.List;
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
        schedule.getWorkers().remove(worker);
    }


    @Transactional
    public void deleteSchedule(List<Worker> allWorkers, Schedule schedule) {
        for (Worker worker: allWorkers) {
            worker.getSchedules().remove(schedule);
        }
        scheduleRepository.delete(schedule);
    }

    @Transactional
    public void addScheduleToWorker(Schedule chosenSchedule, Worker worker) {
        worker.getSchedules().add(chosenSchedule);
        chosenSchedule.getWorkers().add(worker);
    }
}

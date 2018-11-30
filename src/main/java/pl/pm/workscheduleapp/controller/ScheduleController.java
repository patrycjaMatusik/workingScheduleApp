package pl.pm.workscheduleapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.ScheduleRepository;
import pl.pm.workscheduleapp.repository.WorkerRepository;
import pl.pm.workscheduleapp.service.ScheduleService;

import java.util.List;
import java.util.Optional;

@Controller
public class ScheduleController {

    private ScheduleRepository scheduleRepository;
    private ScheduleService scheduleService;
    private WorkerRepository workerRepository;

    public ScheduleController(ScheduleRepository scheduleRepository, ScheduleService scheduleService, WorkerRepository workerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
        this.workerRepository = workerRepository;
    }

    @PostMapping("/saveSchedule")
    public String addWorker(Schedule schedule){
        scheduleRepository.save(schedule);
        return "success";
    }

    @RequestMapping(value = "/scheduleManagement", params = "editSchedule", method = RequestMethod.POST)
    public String editSchedule(Model model, @RequestParam(name = "schedule_id") Long schedule_id){
        Optional<Schedule> scheduleById = scheduleRepository.findById(schedule_id);
        scheduleById.ifPresent(loadedSchedule -> model.addAttribute("mySchedule", loadedSchedule));
        return scheduleById.map(loadedSchedule -> "updateSchedule").orElse("noSchedule");
    }

    @GetMapping("/editSchedule/{id}")
    public String updateSchedule(@PathVariable(name = "id") Long id, Schedule schedule){
        scheduleService.updateSchedule(id, schedule.getWork_date(), schedule.getStart_working_hour(), schedule.getEnd_working_hour());
        return "successUpdating";
    }

    @RequestMapping(value = "/processSchedules", params = "delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteSchedule(Schedule schedule, Worker worker){
        Optional<Schedule> scheduleById = scheduleRepository.findById(schedule.getSchedule_id());
        Schedule scheduleToRemove = scheduleById.get();
        Optional<Worker> workerById = workerRepository.findById(worker.getWorker_id());
        Worker workerToRemoveFrom = workerById.get();
        scheduleService.deleteScheduleFromWorker(scheduleToRemove, workerToRemoveFrom);
        return "Deleted";
    }

    @GetMapping("/manageWorkSchedules")
    public String manageWorkSchedule(Model model){
        List<Schedule> allSchedules = scheduleRepository.findAll();
        model.addAttribute(allSchedules);
        return "manageWorkSchedules";
    }

    @RequestMapping(value = "scheduleManagement", params = "addSchedule", method = RequestMethod.POST)
    public String addSchedule(Model model){
        model.addAttribute("schedule", new Schedule());
        return "addSchedule";
    }
}

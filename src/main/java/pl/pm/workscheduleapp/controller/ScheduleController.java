package pl.pm.workscheduleapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.repository.ScheduleRepository;
import pl.pm.workscheduleapp.service.ScheduleService;

import java.util.Optional;

@Controller
public class ScheduleController {

    private ScheduleRepository scheduleRepository;
    private ScheduleService scheduleService;

    public ScheduleController(ScheduleRepository scheduleRepository, ScheduleService scheduleService) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleService = scheduleService;
    }

    @GetMapping("/addSchedule")
    public String addSchedule(Model model){
        model.addAttribute("schedule", new Schedule());
        return "addSchedule";
    }

    @PostMapping("/saveSchedule")
    public String addWorker(Schedule schedule){
        scheduleRepository.save(schedule);
        return "success";
    }

    @RequestMapping(value = "/processSchedules", params = "edit", method = RequestMethod.POST)
    public String editSchedule(Model model, Schedule schedule){
        Optional<Schedule> scheduleById = scheduleRepository.findById(schedule.getId());
        scheduleById.ifPresent(loadedSchedule -> model.addAttribute("mySchedule", loadedSchedule));
        return scheduleById.map(loadedSchedule -> "updateSchedule").orElse("noSchedule");
    }

    @GetMapping("/editSchedule/{id}")
    public String updateSchedule(@PathVariable(name = "id") Long id, Schedule schedule){
        scheduleService.updateSchedule(id, schedule.getWork_date(), schedule.getStart_working_hour(), schedule.getEnd_working_hour());
        return "successUpdating";
    }
}

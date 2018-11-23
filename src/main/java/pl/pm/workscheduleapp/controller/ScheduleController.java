package pl.pm.workscheduleapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.repository.ScheduleRepository;

@Controller
public class ScheduleController {

    private ScheduleRepository scheduleRepository;

    public ScheduleController(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
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
}

package pl.pm.workscheduleapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pm.workscheduleapp.model.Schedule;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.ScheduleRepository;
import pl.pm.workscheduleapp.repository.WorkerRepository;
import pl.pm.workscheduleapp.service.ScheduleService;

import java.sql.SQLIntegrityConstraintViolationException;
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
    public String addWorker(Schedule schedule) {
        scheduleRepository.save(schedule);
        return "success";
    }

    @RequestMapping(value = "/scheduleManagement", params = "editSchedule", method = RequestMethod.POST)
    public String editSchedule(Model model, @RequestParam(name = "schedule_id", required = false) Long schedule_id) {
        if (schedule_id != null) {
            Optional<Schedule> scheduleById = scheduleRepository.findById(schedule_id);
            scheduleById.ifPresent(loadedSchedule -> model.addAttribute("mySchedule", loadedSchedule));
            return scheduleById.map(loadedSchedule -> "updateSchedule").orElse("noSchedule");
        } else {
            return "chooseOption";
        }
    }

    @GetMapping("/editSchedule/{id}")
    public String updateSchedule(@PathVariable(name = "id") Long id, Schedule schedule) {
        scheduleService.updateSchedule(id, schedule.getWork_date(), schedule.getStart_working_hour(), schedule.getEnd_working_hour());
        return "successUpdating";
    }

    @RequestMapping(value = "/processSchedules", params = "delete", method = RequestMethod.POST)
    public ModelAndView deleteSchedule(Schedule schedule, Worker worker) {
        Optional<Schedule> scheduleById = scheduleRepository.findById(schedule.getSchedule_id());
        Schedule scheduleToRemove = scheduleById.get();
        Optional<Worker> workerById = workerRepository.findById(worker.getWorker_id());
        Worker workerToRemoveFrom = workerById.get();
        scheduleService.deleteScheduleFromWorker(scheduleToRemove, workerToRemoveFrom);
        return new ModelAndView("redirect:/worker/" + workerToRemoveFrom.getSurname() + "/" + workerToRemoveFrom.getName());
    }

    @GetMapping("/manageWorkSchedules")
    public String manageWorkSchedules(Model model) {
        List<Schedule> allSchedules = scheduleRepository.findAll();
        model.addAttribute(allSchedules);
        return "manageWorkSchedules";
    }

    @RequestMapping(value = "scheduleManagement", params = "addSchedule", method = RequestMethod.POST)
    public String addSchedule(Model model) {
        model.addAttribute("schedule", new Schedule());
        return "addSchedule";
    }

    @RequestMapping(value = "scheduleManagement", params = "deleteSchedule", method = RequestMethod.POST)
    public String deleteSchedule(Model model, @RequestParam(name = "schedule_id", required = false) Long schedule_id) {
        if (schedule_id != null) {
            Optional<Schedule> byId = scheduleRepository.findById(schedule_id);
            Schedule schedule = byId.get();
            List<Worker> allWorkers = workerRepository.findAll();
            scheduleService.deleteSchedule(allWorkers, schedule);
            return manageWorkSchedules(model);
        } else {
            return "chooseOption";
        }
    }

    @PostMapping("/addScheduleToWorker")
    public String addScheduleToWorker(Model model, @RequestParam(name = "worker_id") Long worker_id) {
        Optional<Worker> workerById = workerRepository.findById(worker_id);
        Worker worker = workerById.get();
        model.addAttribute("worker", worker);
        List<Schedule> allSchedules = scheduleRepository.findAll();
        model.addAttribute("allSchedules", allSchedules);
        return "addScheduleToWorker";
    }

    @PostMapping("addChosenScheduleToWorker")
    public ModelAndView addChosenScheduleToWorker(@RequestParam(name = "schedule_id", required = false) Long schedule_id, @RequestParam(name = "worker_id") Long worker_id) {
        if (schedule_id != null) {
            Optional<Schedule> scheduleById = scheduleRepository.findById(schedule_id);
            Schedule chosenSchedule = scheduleById.get();
            Optional<Worker> workerById = workerRepository.findById(worker_id);
            Worker worker = workerById.get();
            try {
                scheduleService.addScheduleToWorker(chosenSchedule, worker);
            } catch (Exception e) {
                return new ModelAndView("/scheduleAlreadyAdded.html");
            }
            return new ModelAndView("redirect:/worker/" + worker.getSurname() + "/" + worker.getName());
        } else {
            return new ModelAndView("chooseOption");
        }
    }
}


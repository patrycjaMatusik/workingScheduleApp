package pl.pm.workscheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.WorkerRepository;
import pl.pm.workscheduleapp.service.WorkerService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class WorkerController {

    private WorkerRepository workerRepository;
    private WorkerService workerService;

    @Autowired
    public WorkerController(WorkerRepository workerRepository, WorkerService workerService) {
        this.workerRepository = workerRepository;
        this.workerService = workerService;
    }

    @GetMapping("/add")
    public String addWorker(Model model) {
        model.addAttribute("worker", new Worker());
        return "addWorker";
    }

    @PostMapping("/save")
    public String addWorker(Worker worker) {
        workerRepository.save(worker);
        return "/success";
    }

    @GetMapping("/")
    public String allWorkers(Model model) {
        List<Worker> allWorkers = workerRepository.findAll();
        model.addAttribute("workers", allWorkers);
        return "allWorkers";
    }

    @RequestMapping(value = "/processMainPageForm", params = "delete", method = RequestMethod.POST)
    public ModelAndView delete(Worker worker) {
        return new ModelAndView("redirect:/delete/" + worker.getSurname() + "/" + worker.getName());
    }

    @RequestMapping(value = "/processMainPageForm", params = "details", method = RequestMethod.POST)
    public ModelAndView details(Worker worker) {
        return new ModelAndView("redirect:/worker/" + worker.getSurname() + "/" + worker.getName());
    }

    @GetMapping("/worker/{surname}/{name}")
    public String getWorker(Model model, @PathVariable("surname") String surname, @PathVariable("name") String name) {
        Optional<Worker> workerBySurnameAndName = workerRepository.findBySurnameAndNameIgnoreCase(surname, name);
        workerBySurnameAndName.ifPresent(worker -> model.addAttribute("worker", worker));
        return workerBySurnameAndName.map(worker -> "singleWorker").orElse("noWorker");
    }

    @Transactional
    @GetMapping("/delete/{surname}/{name}")
    public String deleteWorker(Model model, @PathVariable("surname") String surname, @PathVariable("name") String name) {
        int wasDeleted = workerRepository.deleteBySurnameAndNameIgnoreCase(surname, name);
        model.addAttribute("workerSurname", surname);
        model.addAttribute("workerName", name);
        if (wasDeleted == 1) {
            return ("successfullyDeleted");
        } else {
            return ("errorWhileDeleting");
        }
    }

    @RequestMapping(value = "/processSingleWorker", params = "editWorker", method = RequestMethod.POST)
    public String editSingleWorker(Model model, Worker worker) {
        model.addAttribute("worker", worker);
        return "updateWorker";
    }

    @GetMapping("/update/{surname}/{name}")
    public String updateWorker(@PathVariable(name = "surname") String surname, @PathVariable(name = "name") String name, Worker worker) {
        workerService.updateWorker(surname, name, worker.getSurname(), worker.getName(), worker.getAddress(), worker.getPhoneNumber());
        return "successUpdating";
    }

    @RequestMapping(value = "/processSingleWorker", params = "displayWorkersSchedule", method = RequestMethod.POST)
    public String displaySchedule(Model model, Worker worker) {
        Optional<Worker> workerBySurnameAndName = workerRepository.findBySurnameAndNameIgnoreCase(worker.getSurname(), worker.getName());
        workerBySurnameAndName.ifPresent(loadedWorker -> model.addAttribute("schedules", loadedWorker.getSchedules()));
        model.addAttribute("worker", worker);
        return workerBySurnameAndName.map(loadedWorker -> "displaySchedule").orElse("noWorker");
    }

    @PostMapping("/sort")
    public String sort(Model model, @RequestParam(name = "sortingType") String sortingType, @RequestParam(name = "ascendingChosen") String ascendingChosen) {
        List<Worker> allWorkers = workerRepository.findAll();
        if (sortingType.equals("surname")) {
            if (Boolean.parseBoolean(ascendingChosen)) {
                Collections.sort(allWorkers, Comparator.comparing(worker -> worker.getSurname().toLowerCase()));
            } else {
                Collections.sort(allWorkers, Collections.reverseOrder(Comparator.comparing(worker -> worker.getSurname().toLowerCase())));
            }
        } else if (sortingType.equals("name")) {
            if (Boolean.parseBoolean(ascendingChosen)) {
                Collections.sort(allWorkers, Comparator.comparing(worker -> worker.getName().toLowerCase()));
            } else {
                Collections.sort(allWorkers, Collections.reverseOrder(Comparator.comparing(worker -> worker.getName().toLowerCase())));
            }
        }
        model.addAttribute("workers", allWorkers);
        return "allWorkers";
    }

    @PostMapping("/search")
    public String searchPhrase(Model model, @RequestParam String searchPhrase){
        List<Worker> workers = workerService.searchWorkerLike(searchPhrase);
        model.addAttribute("workers", workers);
        model.addAttribute("oldPhrase", searchPhrase);
        return "allWorkers";
    }
}

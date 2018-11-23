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
    public String addWorker(Model model){
        model.addAttribute("worker", new Worker());
        return "addWorker";
    }

    @PostMapping("/save")
    public String addWorker(Worker worker){
        workerRepository.save(worker);
        return "success";
    }

    @GetMapping("/")
    public String allWorkers(Model model){
        List<Worker> allWorkers = workerRepository.findAll();
        model.addAttribute("workers", allWorkers);
        return "allWorkers";
    }

    @RequestMapping(value = "/processMainPageForm", params = "delete", method = RequestMethod.POST)
    public ModelAndView delete(Worker worker){
        return new ModelAndView("redirect:/delete/"+ worker.getSurname() + "/" + worker.getName());
    }

    @GetMapping("/worker/{surname}/{name}")
    public String getWorker(Model model, @PathVariable("surname") String surname,@PathVariable("name") String name){
        Optional<Worker> workerBySurnameAndName = workerRepository.findBySurnameAndNameIgnoreCase(surname, name);
        workerBySurnameAndName.ifPresent(worker -> model.addAttribute("worker", worker));
        return workerBySurnameAndName.map(worker -> "singleWorker").orElse("noWorker");
    }

    @Transactional
    @GetMapping("/delete/{surname}/{name}")
    public String deleteWorker(Model model, @PathVariable("surname") String surname,@PathVariable("name") String name){
        int wasDeleted = workerRepository.deleteBySurnameAndNameIgnoreCase(surname, name);
        model.addAttribute("workerSurname", surname);
        model.addAttribute("workerName", name);
        if(wasDeleted==1){
            return ("successfullyDeleted");
        }else{
            return ("errorWhileDeleting");
        }
    }

    @GetMapping("/update/{surname}/{name}")
    @ResponseBody
    public String updateWorker(@PathVariable(name = "surname") String surname, @PathVariable(name = "name") String name, @RequestParam(value = "newSurname") String newSurname, @RequestParam(value = "newName") String newName){
        workerService.updateWorker(surname, name, newSurname, newName);
        return "Updated";
    }
}

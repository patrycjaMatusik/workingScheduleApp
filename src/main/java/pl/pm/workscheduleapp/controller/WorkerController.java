package pl.pm.workscheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.WorkerRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
public class WorkerController {

    private WorkerRepository workerRepository;

    @Autowired
    public WorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
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

    @GetMapping("allWorkers")
    public String allWorkers(Model model){
        List<Worker> allWorkers = workerRepository.findAll();
        model.addAttribute("workers", allWorkers);
        return "allWorkers";
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
}

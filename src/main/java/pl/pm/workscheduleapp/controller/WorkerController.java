package pl.pm.workscheduleapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.WorkerRepository;

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
}

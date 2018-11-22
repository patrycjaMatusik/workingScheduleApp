package pl.pm.workscheduleapp.service;

import org.springframework.stereotype.Service;
import pl.pm.workscheduleapp.model.Worker;
import pl.pm.workscheduleapp.repository.WorkerRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class WorkerService {

    private WorkerRepository workerRepository;

    public WorkerService(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @Transactional
    public void updateWorker(String surname, String name, String newSurname, String newName){
        Optional<Worker> bySurnameAndNameIgnoreCase = workerRepository.findBySurnameAndNameIgnoreCase(surname, name);
        Worker workerToUpdate = bySurnameAndNameIgnoreCase.get();
        workerToUpdate.setName(newName);
        workerToUpdate.setSurname(newSurname);
    }
}

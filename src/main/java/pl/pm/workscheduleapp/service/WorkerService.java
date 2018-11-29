package pl.pm.workscheduleapp.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import pl.pm.workscheduleapp.model.Schedule;
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
    public void updateWorker(String surname, String name, String newSurname, String newName, String neAddress, String newPhoneNumber){
        Optional<Worker> bySurnameAndNameIgnoreCase = workerRepository.findBySurnameAndNameIgnoreCase(surname, name);
        Worker workerToUpdate = bySurnameAndNameIgnoreCase.get();
        workerToUpdate.setName(newName);
        workerToUpdate.setSurname(newSurname);
        workerToUpdate.setAddress(neAddress);
        workerToUpdate.setPhoneNumber(newPhoneNumber);
    }
}

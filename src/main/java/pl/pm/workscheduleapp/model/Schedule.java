package pl.pm.workscheduleapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String work_date;
    private String start_working_hour;
    private String end_working_hour;
    @ManyToMany(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    }, mappedBy = "schedules")
    private List<Worker> workers;

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        this.work_date = work_date;
    }

    public String getStart_working_hour() {
        return start_working_hour;
    }

    public void setStart_working_hour(String start_working_hour) {
        this.start_working_hour = start_working_hour;
    }

    public String getEnd_working_hour() {
        return end_working_hour;
    }

    public void setEnd_working_hour(String end_working_hour) {
        this.end_working_hour = end_working_hour;
    }
}

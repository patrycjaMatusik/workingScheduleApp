package pl.pm.workscheduleapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date work_date;
    private double start_working_hour;
    private double end_working_hour;
    @ManyToOne
    private Worker worker;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }

    public double getStart_working_hour() {
        return start_working_hour;
    }

    public void setStart_working_hour(double start_working_hour) {
        this.start_working_hour = start_working_hour;
    }

    public double getEnd_working_hour() {
        return end_working_hour;
    }

    public void setEnd_working_hour(double end_working_hour) {
        this.end_working_hour = end_working_hour;
    }
}

package pl.pm.workscheduleapp.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date work_date;
    @Temporal(TemporalType.TIME)
    private Date start_working_hour;
    @Temporal(TemporalType.TIME)
    private Date end_working_hour;
    @ManyToMany
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

    public Date getWork_date() {
        return work_date;
    }

    public void setWork_date(String work_date) {
        try {
            this.work_date = new SimpleDateFormat("yyyy-MM-dd").parse(work_date);
        } catch (ParseException e){
            e.printStackTrace();
        }
    }

    public void setWork_date(Date work_date) {
        this.work_date = work_date;
    }

    public Date getStart_working_hour() {
        return start_working_hour;
    }

    public void setStart_working_hour(String start_working_hour) {

        try {
            this.start_working_hour = new SimpleDateFormat("hh:mm").parse(start_working_hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setStart_working_hour(Date start_working_hour) {
        this.start_working_hour = start_working_hour;
    }

    public Date getEnd_working_hour() {
        return end_working_hour;
    }

    public void setEnd_working_hour(String end_working_hour) {
        try {
            this.end_working_hour = new SimpleDateFormat("hh:mm").parse(end_working_hour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setEnd_working_hour(Date end_working_hour) {
        this.end_working_hour = end_working_hour;
    }
}

package pl.pm.workscheduleapp.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
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

    public void setWork_date(String work_date) {
        try {
            this.work_date = new SimpleDateFormat("yyyy-MM-dd").parse(work_date);
        } catch (ParseException e){
            e.printStackTrace();
        }
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
}

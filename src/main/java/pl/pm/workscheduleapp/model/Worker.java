package pl.pm.workscheduleapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long worker_id;
    @Column(length = 100)
    private String name;
    @Column(length = 100)
    private String surname;
    private String address;
    @Column(length = 12)
    private String phoneNumber;
    @ManyToMany(cascade =
            {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
            })
    @JoinTable(name = "workers_schedules",
            joinColumns = @JoinColumn(name = "worker_id", referencedColumnName = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id"))

    private List<Schedule> schedules;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Long getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(Long worker_id) {
        this.worker_id = worker_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

package dnaaaaahtac.wooriforei.domain.scheduler.entity;

import dnaaaaahtac.wooriforei.domain.scheduler.Schedulable;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Schedulers")
public class Scheduler extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulerId;

    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulerMember> schedulerMembers = new ArrayList<>();

    @Column(nullable = false)
    private String schedulerName;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulerActivity> schedulerActivities = new ArrayList<>();

    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulerHotel> schedulerHotels = new ArrayList<>();

    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulerInformation> schedulerInformations = new ArrayList<>();

    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulerLandmark> schedulerLandmarks = new ArrayList<>();

    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulerRestaurant> schedulerRestaurants = new ArrayList<>();

    @OneToMany(mappedBy = "scheduler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SchedulerSeoulGoods> schedulerSeoulGoods = new ArrayList<>();

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Schedulable> getEvents() {
        List<Schedulable> events = new ArrayList<>();
        events.addAll(this.schedulerActivities);
        events.addAll(this.schedulerHotels);
        events.addAll(this.schedulerInformations);
        events.addAll(this.schedulerLandmarks);
        events.addAll(this.schedulerRestaurants);
        events.addAll(this.schedulerSeoulGoods);

        return events;
    }
}
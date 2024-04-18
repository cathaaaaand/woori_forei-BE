package dnaaaaahtac.wooriforei.domain.scheduler.entity;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Activity;
import dnaaaaahtac.wooriforei.domain.scheduler.Schedulable;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "SchedulerActivities")
public class SchedulerActivity extends BaseTimeEntity implements Schedulable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulerActivityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedulerId")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activityId")
    private Activity activity;

    @Column(nullable = false)
    private LocalDateTime visitStart;

    @Column(nullable = false)
    private LocalDateTime visitEnd;

    public SchedulerActivity() {
    }

    public SchedulerActivity(Scheduler scheduler, Activity activity, LocalDateTime visitStart, LocalDateTime visitEnd) {
        this.scheduler = scheduler;
        this.activity = activity;
        this.visitStart = visitStart;
        this.visitEnd = visitEnd;
    }

    public void setSchedulerActivityId(Long schedulerActivityId) {
        this.schedulerActivityId = schedulerActivityId;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }

    @Override
    public Long getEventId() {
        return this.schedulerActivityId;
    }

    @Override
    public LocalDateTime getStartTime() {
        return this.visitStart;
    }

    @Override
    public LocalDateTime getEndTime() {
        return this.visitEnd;
    }
}

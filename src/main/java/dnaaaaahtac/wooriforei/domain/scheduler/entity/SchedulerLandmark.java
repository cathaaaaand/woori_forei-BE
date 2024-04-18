package dnaaaaahtac.wooriforei.domain.scheduler.entity;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Landmark;
import dnaaaaahtac.wooriforei.domain.scheduler.Schedulable;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "SchedulerLandmarks")
public class SchedulerLandmark extends BaseTimeEntity implements Schedulable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduler_id")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "landmark_id")
    private Landmark landmark;

    @Column(nullable = false)
    private LocalDateTime visitStart;

    @Column(nullable = false)
    private LocalDateTime visitEnd;

    public SchedulerLandmark() {
    }

    public SchedulerLandmark(Scheduler scheduler, Landmark landmark, LocalDateTime visitStart, LocalDateTime visitEnd) {
        this.scheduler = scheduler;
        this.landmark = landmark;
        this.visitStart = visitStart;
        this.visitEnd = visitEnd;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setLandmark(Landmark landmark) {
        this.landmark = landmark;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }

    @Override
    public Long getEventId() {
        return this.id;
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

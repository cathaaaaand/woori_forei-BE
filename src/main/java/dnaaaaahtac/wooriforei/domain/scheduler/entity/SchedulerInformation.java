package dnaaaaahtac.wooriforei.domain.scheduler.entity;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Information;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "SchedulerInformations")
public class SchedulerInformation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedulerId")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "informationId")
    private Information information;

    @Column(nullable = false)
    private LocalDateTime visitStart;

    @Column(nullable = false)
    private LocalDateTime visitEnd;

    protected SchedulerInformation() {
    }

    public SchedulerInformation(Scheduler scheduler, Information information, LocalDateTime visitStart, LocalDateTime visitEnd) {
        this.scheduler = scheduler;
        this.information = information;
        this.visitStart = visitStart;
        this.visitEnd = visitEnd;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }
}
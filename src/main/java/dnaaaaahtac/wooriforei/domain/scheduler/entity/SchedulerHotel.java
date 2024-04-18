package dnaaaaahtac.wooriforei.domain.scheduler.entity;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Hotel;
import dnaaaaahtac.wooriforei.domain.scheduler.Schedulable;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "SchedulerHotels")
public class SchedulerHotel extends BaseTimeEntity implements Schedulable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedulerId")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotelId")
    private Hotel hotel;

    @Column(nullable = false)
    private LocalDateTime stayStart;

    @Column(nullable = false)
    private LocalDateTime stayEnd;

    public SchedulerHotel(Scheduler scheduler, Hotel hotel, LocalDateTime stayStart, LocalDateTime stayEnd) {
        this.scheduler = scheduler;
        this.hotel = hotel;
        this.stayStart = stayStart;
        this.stayEnd = stayEnd;
    }

    protected SchedulerHotel() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public void setStayStart(LocalDateTime stayStart) {
        this.stayStart = stayStart;
    }

    public void setStayEnd(LocalDateTime stayEnd) {
        this.stayEnd = stayEnd;
    }

    @Override
    public Long getEventId() {
        return this.id;
    }

    @Override
    public LocalDateTime getStartTime() {
        return this.stayStart;
    }

    @Override
    public LocalDateTime getEndTime() {
        return this.stayEnd;
    }
}

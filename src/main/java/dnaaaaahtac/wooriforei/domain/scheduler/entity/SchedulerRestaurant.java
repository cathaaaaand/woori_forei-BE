package dnaaaaahtac.wooriforei.domain.scheduler.entity;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Restaurant;
import dnaaaaahtac.wooriforei.domain.scheduler.Schedulable;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "SchedulerRestaurants")
public class SchedulerRestaurant extends BaseTimeEntity implements Schedulable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedulerId")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurantId")
    private Restaurant restaurant;

    @Column(nullable = false)
    private LocalDateTime visitStart;

    @Column(nullable = false)
    private LocalDateTime visitEnd;

    public SchedulerRestaurant(Scheduler scheduler, Restaurant restaurant, LocalDateTime visitStart, LocalDateTime visitEnd) {
        this.scheduler = scheduler;
        this.restaurant = restaurant;
        this.visitStart = visitStart;
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
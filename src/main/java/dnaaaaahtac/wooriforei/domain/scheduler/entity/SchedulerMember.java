package dnaaaaahtac.wooriforei.domain.scheduler.entity;

import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.global.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "SchedulerMembers")
public class SchedulerMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schedulerMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedulerId")
    private Scheduler scheduler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    protected SchedulerMember() {
    }

    // 필요한 인자를 받는 생성자
    public SchedulerMember(Scheduler scheduler, User user) {
        this.scheduler = scheduler;
        this.user = user;
    }

    public void setSchedulerMemberId(Long schedulerMemberId) {
        this.schedulerMemberId = schedulerMemberId;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
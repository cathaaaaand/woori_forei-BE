package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerActivityRequestDTO {

    private Long activityId;

    private LocalDateTime visitStart;

    private LocalDateTime visitEnd;

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }
}

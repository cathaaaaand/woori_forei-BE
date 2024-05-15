package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerActivityRequestDTO {

    private Long activityId;

    private String visitStart;

    private String visitEnd;

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public void setVisitStart(String visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(String visitEnd) {
        this.visitEnd = visitEnd;
    }
}

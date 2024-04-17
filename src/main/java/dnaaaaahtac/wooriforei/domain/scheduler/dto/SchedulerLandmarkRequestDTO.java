package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerLandmarkRequestDTO {

    @NotNull
    private Long landmarkId;

    @NotNull
    private LocalDateTime visitStart;

    @NotNull
    private LocalDateTime visitEnd;

    public void setLandmarkId(Long landmarkId) {
        this.landmarkId = landmarkId;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }
}

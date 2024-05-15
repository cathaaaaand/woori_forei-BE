package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerLandmarkRequestDTO {

    @NotNull
    private Long landmarkId;

    @NotNull
    private String visitStart;

    @NotNull
    private String visitEnd;

    public void setLandmarkId(Long landmarkId) {
        this.landmarkId = landmarkId;
    }

    public void setVisitStart(String visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(String visitEnd) {
        this.visitEnd = visitEnd;
    }
}

package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerInformationRequestDTO {

    private Long informationId;

    private LocalDateTime visitStart;

    private LocalDateTime visitEnd;

    public void setInformationId(Long informationId) {
        this.informationId = informationId;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }
}

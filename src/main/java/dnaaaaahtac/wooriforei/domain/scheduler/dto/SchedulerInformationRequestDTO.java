package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerInformationRequestDTO {

    private Long informationId;

    private String visitStart;

    private String visitEnd;

    public void setInformationId(Long informationId) {
        this.informationId = informationId;
    }

    public void setVisitStart(String visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(String visitEnd) {
        this.visitEnd = visitEnd;
    }
}

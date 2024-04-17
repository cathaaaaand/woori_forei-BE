package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerSeoulGoodsRequestDTO {

    @NotNull
    private Long goodsId;

    @NotNull
    private LocalDateTime visitStart;

    @NotNull
    private LocalDateTime visitEnd;

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }
}

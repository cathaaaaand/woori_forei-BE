package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerSeoulGoodsRequestDTO {

    @NotNull
    private Long goodsId;

    @NotNull
    private String visitStart;

    @NotNull
    private String visitEnd;

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public void setVisitStart(String visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(String visitEnd) {
        this.visitEnd = visitEnd;
    }
}

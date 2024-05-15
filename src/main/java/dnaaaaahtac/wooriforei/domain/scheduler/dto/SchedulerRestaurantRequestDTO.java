package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerRestaurantRequestDTO {

    @NotNull
    private Long restaurantId;

    @NotNull
    private String visitStart;

    @NotNull
    private String visitEnd;

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setVisitStart(String visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(String visitEnd) {
        this.visitEnd = visitEnd;
    }
}

package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerRestaurantRequestDTO {

    @NotNull
    private Long restaurantId;

    @NotNull
    private LocalDateTime visitStart;

    @NotNull
    private LocalDateTime visitEnd;

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public void setVisitStart(LocalDateTime visitStart) {
        this.visitStart = visitStart;
    }

    public void setVisitEnd(LocalDateTime visitEnd) {
        this.visitEnd = visitEnd;
    }
}

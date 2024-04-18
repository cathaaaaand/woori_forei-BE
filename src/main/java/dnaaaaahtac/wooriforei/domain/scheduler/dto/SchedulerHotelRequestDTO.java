package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerHotelRequestDTO {

    private Long hotelId;

    private LocalDateTime stayStart;

    private LocalDateTime stayEnd;

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setStayStart(LocalDateTime stayStart) {
        this.stayStart = stayStart;
    }

    public void setStayEnd(LocalDateTime stayEnd) {
        this.stayEnd = stayEnd;
    }
}

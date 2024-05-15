package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SchedulerHotelRequestDTO {

    private Long hotelId;

    private String stayStart;

    private String stayEnd;

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setStayStart(String stayStart) {
        this.stayStart = stayStart;
    }

    public void setStayEnd(String stayEnd) {
        this.stayEnd = stayEnd;
    }
}

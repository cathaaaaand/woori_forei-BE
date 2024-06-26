package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SchedulerRequestDTO {

    private Long schedulerId;

    @NotBlank(message = "스케줄러 이름은 필수입니다.", groups = Create.class)
    @Size(min = 1, message = "스케줄러 이름은 최소 1글자 이상이어야 합니다.", groups = Update.class)
    private String schedulerName;

    @NotNull(message = "시작 날짜는 필수입니다.", groups = Create.class)
    private String startDate;

    @NotNull(message = "종료 날짜는 필수입니다.", groups = Create.class)
    private String endDate;

    private List<String> memberEmails;

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public interface Create {
    }

    public interface Update {
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setMemberEmails(List<String> memberEmails) {
        this.memberEmails = memberEmails;
    }
}

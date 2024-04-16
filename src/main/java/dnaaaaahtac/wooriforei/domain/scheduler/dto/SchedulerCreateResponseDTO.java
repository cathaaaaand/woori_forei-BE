package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import dnaaaaahtac.wooriforei.domain.user.dto.UserDetailResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SchedulerCreateResponseDTO {

    private Long schedulerId;
    private String schedulerName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<UserDetailResponseDTO> members;

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public void setSchedulerName(String schedulerName) {
        this.schedulerName = schedulerName;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setMembers(List<UserDetailResponseDTO> members) {
        this.members = members;
    }
}

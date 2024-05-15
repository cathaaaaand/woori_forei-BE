package dnaaaaahtac.wooriforei.domain.scheduler.dto;

import dnaaaaahtac.wooriforei.domain.user.dto.UserDetailResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class SchedulerResponseDTO {

    private Long schedulerId;
    private String schedulerName;
    private String startDate;
    private String endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<UserDetailResponseDTO> members;
    private List<OpenAPIDetailsDTO> openAPIs;

    @Getter
    public static class OpenAPIDetailsDTO {
        private Long id;
        private String name;
        private String visitStart;
        private String visitEnd;
        private String type;

        public OpenAPIDetailsDTO(Long id, String name, String visitStart, String visitEnd, String type) {
            this.id = id;
            this.name = name;
            this.visitStart = visitStart;
            this.visitEnd = visitEnd;
            this.type = type;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setVisitStart(String visitStart) {
            this.visitStart = visitStart;
        }

        public void setVisitEnd(String visitEnd) {
            this.visitEnd = visitEnd;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public void setSchedulerId(Long schedulerId) {
        this.schedulerId = schedulerId;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public void setMembers(List<UserDetailResponseDTO> members) {
        this.members = members;
    }

    public void setOpenAPIs(List<OpenAPIDetailsDTO> openAPIs) {
        this.openAPIs = openAPIs;
    }
}

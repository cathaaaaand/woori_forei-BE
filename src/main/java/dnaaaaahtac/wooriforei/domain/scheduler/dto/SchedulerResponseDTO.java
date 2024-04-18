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
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<UserDetailResponseDTO> members;
    private List<OpenAPIDetailsDTO> openAPIs;

    @Getter
    public static class OpenAPIDetailsDTO {
        private Long id;
        private String name;
        private LocalDateTime visitStart;
        private LocalDateTime visitEnd;
        private String type;


        public OpenAPIDetailsDTO() {
        }

        public OpenAPIDetailsDTO(Long id, String name, LocalDateTime visitStart, LocalDateTime visitEnd, String type) {
            this.id = id;
            this.name = name;
            this.visitStart = visitStart;
            this.visitEnd = visitEnd;
            this.type = type;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getVisitStart() {
            return visitStart;
        }

        public void setVisitStart(LocalDateTime visitStart) {
            this.visitStart = visitStart;
        }

        public LocalDateTime getVisitEnd() {
            return visitEnd;
        }

        public void setVisitEnd(LocalDateTime visitEnd) {
            this.visitEnd = visitEnd;
        }

        public String getType() {
            return type;
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

    public void setOpenAPIs(List<OpenAPIDetailsDTO> openAPIs) {
        this.openAPIs = openAPIs;
    }
}

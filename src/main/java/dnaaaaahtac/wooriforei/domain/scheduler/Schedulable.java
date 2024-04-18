package dnaaaaahtac.wooriforei.domain.scheduler;

import java.time.LocalDateTime;

public interface Schedulable {
    Long getEventId();

    LocalDateTime getStartTime();

    LocalDateTime getEndTime();
}

package dnaaaaahtac.wooriforei.domain.scheduler.repository;

import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerLandmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerLandmarkRepository extends JpaRepository<SchedulerLandmark, Long> {
}

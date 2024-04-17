package dnaaaaahtac.wooriforei.domain.scheduler.repository;

import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerInformationRepository extends JpaRepository<SchedulerInformation, Long> {
}
package dnaaaaahtac.wooriforei.domain.scheduler.repository;

import dnaaaaahtac.wooriforei.domain.scheduler.entity.Scheduler;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerHotel;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerLandmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerLandmarkRepository extends JpaRepository<SchedulerLandmark, Long> {

    List<SchedulerLandmark> findByScheduler(Scheduler scheduler);
}

package dnaaaaahtac.wooriforei.domain.scheduler.repository;

import dnaaaaahtac.wooriforei.domain.scheduler.entity.Scheduler;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerHotelRepository extends JpaRepository<SchedulerHotel, Long> {

    List<SchedulerHotel> findByScheduler(Scheduler scheduler);
}

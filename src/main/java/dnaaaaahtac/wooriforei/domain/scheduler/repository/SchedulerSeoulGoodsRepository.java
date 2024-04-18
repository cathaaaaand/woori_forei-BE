package dnaaaaahtac.wooriforei.domain.scheduler.repository;

import dnaaaaahtac.wooriforei.domain.scheduler.entity.Scheduler;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerHotel;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerSeoulGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerSeoulGoodsRepository extends JpaRepository<SchedulerSeoulGoods, Long> {

    List<SchedulerSeoulGoods> findByScheduler(Scheduler scheduler);
}

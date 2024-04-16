package dnaaaaahtac.wooriforei.domain.scheduler.repository;

import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerMemberRepository extends JpaRepository<SchedulerMember, Long> {

    List<SchedulerMember> findByScheduler_SchedulerId(Long schedulerId);

}

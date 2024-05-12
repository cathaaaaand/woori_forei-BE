package dnaaaaahtac.wooriforei.domain.scheduler.repository;

import dnaaaaahtac.wooriforei.domain.scheduler.entity.Scheduler;
import dnaaaaahtac.wooriforei.domain.scheduler.entity.SchedulerMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchedulerMemberRepository extends JpaRepository<SchedulerMember, Long> {

    List<SchedulerMember> findByScheduler_SchedulerId(Long schedulerId);

    List<SchedulerMember> findByScheduler(Scheduler scheduler);

    // 사용자 ID를 통해 해당 사용자의 스케줄러 아이디와 이름을 조회
    @Query("SELECT sm.scheduler.schedulerId as schedulerId, sm.scheduler.schedulerName as schedulerName " +
            "FROM SchedulerMember sm WHERE sm.user.userId = :userId")
    List<Object[]> findSchedulerInfoByUserId(@Param("userId") Long userId);

    void deleteAllByScheduler(Scheduler scheduler);

}

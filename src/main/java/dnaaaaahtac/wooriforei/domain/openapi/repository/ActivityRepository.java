package dnaaaaahtac.wooriforei.domain.openapi.repository;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long>{

    Optional<Activity> findBySvcid(String svcid);
}

package dnaaaaahtac.wooriforei.domain.openapi.repository;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpenApiRepository extends JpaRepository<Information, Long> {
}

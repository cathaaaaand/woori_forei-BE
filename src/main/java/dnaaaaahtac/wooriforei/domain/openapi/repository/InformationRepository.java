package dnaaaaahtac.wooriforei.domain.openapi.repository;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InformationRepository extends JpaRepository<Information, Long> {
    // 고유 식별 조합을 통해 정보 조회
    Optional<Information> findByTrsmicnmAndSigngunm(String trsmicnm, String signgunm);
}

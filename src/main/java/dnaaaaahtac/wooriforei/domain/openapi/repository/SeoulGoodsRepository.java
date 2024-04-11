package dnaaaaahtac.wooriforei.domain.openapi.repository;

import dnaaaaahtac.wooriforei.domain.openapi.entity.SeoulGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeoulGoodsRepository extends JpaRepository<SeoulGoods, Long> {

    Optional<SeoulGoods> findByNmAndTel(String nm, String tel);

}

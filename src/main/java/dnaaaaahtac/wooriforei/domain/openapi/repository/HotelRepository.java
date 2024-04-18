package dnaaaaahtac.wooriforei.domain.openapi.repository;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByMainKey(String mainKey);
}

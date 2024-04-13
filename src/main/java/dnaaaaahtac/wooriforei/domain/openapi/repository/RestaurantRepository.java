package dnaaaaahtac.wooriforei.domain.openapi.repository;

import dnaaaaahtac.wooriforei.domain.openapi.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Optional<Restaurant> findByPostSn (String postSn);
}

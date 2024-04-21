package dnaaaaahtac.wooriforei.domain.image.repository;

import dnaaaaahtac.wooriforei.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}

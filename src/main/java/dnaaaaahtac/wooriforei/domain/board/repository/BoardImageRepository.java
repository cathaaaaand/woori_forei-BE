package dnaaaaahtac.wooriforei.domain.board.repository;

import dnaaaaahtac.wooriforei.domain.board.entity.BoardImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardImageRepository extends JpaRepository<BoardImage, Long> {

    Optional<BoardImage> findByOriginName (String origiName);

}
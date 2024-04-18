package dnaaaaahtac.wooriforei.domain.board.repository;

import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}

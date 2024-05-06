package dnaaaaahtac.wooriforei.domain.board.repository;

import dnaaaaahtac.wooriforei.domain.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {

    int countByBoard_BoardId(Long boardId);

    boolean existsByUserIdAndBoard_BoardId(Long userId, Long boardId);

    BoardLike findByUserIdAndBoard_BoardId(Long userId, Long boardId);
}

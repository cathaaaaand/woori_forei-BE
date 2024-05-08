package dnaaaaahtac.wooriforei.domain.board.repository;

import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByUserId(Long userId);

    @Query("SELECT b FROM Board b ORDER BY b.boardLike DESC, b.createdAt DESC")
    List<Board> findTopLikedBoards();
}

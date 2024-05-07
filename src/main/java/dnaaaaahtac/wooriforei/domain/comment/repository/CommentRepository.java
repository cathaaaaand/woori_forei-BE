package dnaaaaahtac.wooriforei.domain.comment.repository;

import dnaaaaahtac.wooriforei.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBoard_BoardId(Long boardId);

    List<Comment> findByUser_UserId(Long userId);
}

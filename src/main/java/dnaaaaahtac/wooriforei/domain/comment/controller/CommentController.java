package dnaaaaahtac.wooriforei.domain.comment.controller;

import dnaaaaahtac.wooriforei.domain.comment.dto.CommentRequestDTO;
import dnaaaaahtac.wooriforei.domain.comment.dto.CommentResponseDTO;
import dnaaaaahtac.wooriforei.domain.comment.service.CommentService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{boardId}")
    public ResponseEntity<CommonResponse<CommentResponseDTO>> createComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId,
            @RequestBody CommentRequestDTO commentRequestDTO) {

        CommentResponseDTO commentResponseDTO =
                commentService.createComment(boardId, userDetails.getUser(), commentRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of("댓글 생성 성공", commentResponseDTO));
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<CommonResponse<List<CommentResponseDTO>>> checkAllComments(@PathVariable Long boardId) {

        List<CommentResponseDTO> commentResponseDTOS = commentService.checkAllComments(boardId);

        return ResponseEntity.ok().body(CommonResponse.of("댓글 전체 조회 성공", commentResponseDTOS));
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonResponse<CommentResponseDTO>> updateComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDTO commentRequestDTO) {

        CommentResponseDTO commentResponseDTO = commentService.updateComment(commentId, userDetails.getUser(), commentRequestDTO);

        return ResponseEntity.ok().body(CommonResponse.of("댓글 업데이트 성공", commentResponseDTO));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long commentId) {

        commentService.deleteComment(commentId, userDetails.getUser());

        return ResponseEntity.ok().body(CommonResponse.of("댓글 삭제 성공", null));
    }

}

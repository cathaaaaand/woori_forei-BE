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

@CrossOrigin(
        origins = {"https://cat.wooriforei.info", "http://localhost:3000", "https://www.wooriforei.info"},
        allowCredentials = "true",
        allowedHeaders = {"Authorization", "Content-Type", "Accept"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
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

    @GetMapping("/mycomment")
    public ResponseEntity<CommonResponse<List<CommentResponseDTO>>> checkMyComments(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<CommentResponseDTO> commentResponseDTO = commentService.checkMyComments(userDetails.getUserId());

        return ResponseEntity.ok().body(CommonResponse.of("내가 작성한 댓글 조회 성공", commentResponseDTO));
    }

}

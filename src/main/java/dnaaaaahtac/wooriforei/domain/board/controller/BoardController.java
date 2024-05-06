package dnaaaaahtac.wooriforei.domain.board.controller;

import dnaaaaahtac.wooriforei.domain.board.dto.BoardRequestDTO;
import dnaaaaahtac.wooriforei.domain.board.dto.BoardResponseDTO;
import dnaaaaahtac.wooriforei.domain.board.service.BoardService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communities")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<CommonResponse<BoardResponseDTO>> createBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(required = false, name = "images") List<MultipartFile> images,
            @RequestPart(name = "request") @Valid BoardRequestDTO boardRequestDTO) {

        BoardResponseDTO boardResponseDTO = boardService.createBoard(userDetails.getUser(), boardRequestDTO, images);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of("게시글 생성 성공", boardResponseDTO));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardResponseDTO>>> checkAllBoards() {

        List<BoardResponseDTO> boardResponseDTO = boardService.getAllBoards();

        return ResponseEntity.ok().body(CommonResponse.of("최신글 전체 조회 성공.", boardResponseDTO));
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommonResponse<BoardResponseDTO>> checkByIdBoard(@PathVariable Long communityId) {

        BoardResponseDTO boardResponseDTO = boardService.getBoardById(communityId);

        return ResponseEntity.ok().body(CommonResponse.of("게시글 단일 조회 성공", boardResponseDTO));
    }

    @PatchMapping("/{communityId}")
    public ResponseEntity<CommonResponse<BoardResponseDTO>> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long communityId,
            @RequestPart(required = false, name = "images") List<MultipartFile> images,
            @RequestPart(name = "request") @Valid BoardRequestDTO boardRequestDTO) {

        BoardResponseDTO boardResponseDTO = boardService.updateBoard(userDetails.getUser(), communityId, boardRequestDTO, images);

        return ResponseEntity.ok().body(CommonResponse.of("게시글 수정 성공", boardResponseDTO));
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<CommonResponse<Void>> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long communityId) {

        boardService.deleteBoard(userDetails.getUser(), communityId);

        return ResponseEntity.ok().body(CommonResponse.of("게시글 삭제 성공", null));
    }

    @GetMapping("/myboard")
    public ResponseEntity<CommonResponse<List<BoardResponseDTO>>> checkMyBoards(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<BoardResponseDTO> myBoardList = boardService.checkMyBoards(userDetails.getUserId());

        return ResponseEntity.ok().body(CommonResponse.of("내가 작성한 게시글 조회 성공", myBoardList));
    }

    @PostMapping("/{boardId}/like")
    public ResponseEntity<CommonResponse<Void>> boardLike(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long boardId) {

        boardService.boardLike(userDetails.getUser(), boardId);
        return ResponseEntity.ok().body(CommonResponse.of("게시글 좋아요/삭제 성공", null));
    }

    @GetMapping("/like")
    public ResponseEntity<CommonResponse<List<BoardResponseDTO>>> likeBoardList() {

        return ResponseEntity.ok().body(CommonResponse.of("인기글 조회 성공", boardService.likeBoardList()));
    }

}


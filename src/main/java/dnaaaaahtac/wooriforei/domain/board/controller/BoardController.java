package dnaaaaahtac.wooriforei.domain.board.controller;

import dnaaaaahtac.wooriforei.domain.board.dto.BoardRequestDTO;
import dnaaaaahtac.wooriforei.domain.board.dto.BoardResponseDTO;
import dnaaaaahtac.wooriforei.domain.board.service.BoradService;
import dnaaaaahtac.wooriforei.global.common.CommonResponse;
import dnaaaaahtac.wooriforei.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communities")
public class BoardController {

    private final BoradService boradService;

    @PostMapping
    public ResponseEntity<CommonResponse<BoardResponseDTO>> createBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid BoardRequestDTO boardRequestDTO) {

        BoardResponseDTO boardResponseDTO = boradService.createBoard(userDetails.getUser(), boardRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.of("게시글 생성 성공", boardResponseDTO));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<BoardResponseDTO>>> checkAllBoards() {

        List<BoardResponseDTO> boardResponseDTO = boradService.getAllBoards();

        return ResponseEntity.ok().body(CommonResponse.of("최신글 전체 조회 성공.", boardResponseDTO));
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommonResponse<BoardResponseDTO>> checkByIdBoard(@PathVariable Long communityId) {

        BoardResponseDTO boardResponseDTO = boradService.getBoardById(communityId);

        return ResponseEntity.ok().body(CommonResponse.of("게시글 단일 조회 성공", boardResponseDTO));
    }

    @PatchMapping("/{communityId}")
    public ResponseEntity<CommonResponse<BoardResponseDTO>> updateBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long communityId,
            @RequestBody @Valid BoardRequestDTO boardRequestDTO) {

        BoardResponseDTO boardResponseDTO = boradService.updateBoard(userDetails.getUser(), communityId, boardRequestDTO);

        return ResponseEntity.ok().body(CommonResponse.of("게시글 수정 성공", boardResponseDTO));
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<CommonResponse<Void>> deleteBoard(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long communityId) {

        boradService.deleteBoard(userDetails.getUser(), communityId);

        return ResponseEntity.ok().body(CommonResponse.of("게시글 삭제 성공", null));
    }

}


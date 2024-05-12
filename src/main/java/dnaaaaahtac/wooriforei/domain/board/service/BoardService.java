package dnaaaaahtac.wooriforei.domain.board.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import dnaaaaahtac.wooriforei.domain.board.dto.BoardRequestDTO;
import dnaaaaahtac.wooriforei.domain.board.dto.BoardResponseDTO;
import dnaaaaahtac.wooriforei.domain.board.entity.Board;
import dnaaaaahtac.wooriforei.domain.board.entity.BoardImage;
import dnaaaaahtac.wooriforei.domain.board.entity.BoardLike;
import dnaaaaahtac.wooriforei.domain.board.repository.BoardImageRepository;
import dnaaaaahtac.wooriforei.domain.board.repository.BoardLikeRepository;
import dnaaaaahtac.wooriforei.domain.board.repository.BoardRepository;
import dnaaaaahtac.wooriforei.domain.comment.dto.CommentResponseDTO;
import dnaaaaahtac.wooriforei.domain.comment.entity.Comment;
import dnaaaaahtac.wooriforei.domain.comment.repository.CommentRepository;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Value("${S3_NAME}")
    private String bucketName;

    private final BoardRepository boardRepository;
    private final BoardImageRepository boardImageRepository;
    private final AmazonS3Client amazonS3Client;
    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public BoardResponseDTO createBoard(User user, BoardRequestDTO boardRequestDTO, List<MultipartFile> multipartFile) {

        validation(boardRequestDTO);

        Board board = new Board();
        board.setUser(user);
        board.setTitle(boardRequestDTO.getTitle());
        board.setContent(boardRequestDTO.getContent());
        board.setBoardLike(0);
        board.setCreatedAt(LocalDateTime.now());
        board.setModifiedAt(LocalDateTime.now());

        if (multipartFile != null && !multipartFile.isEmpty()) {
            // 실제로 파일 데이터를 포함하고 있는 파일만 처리
            List<MultipartFile> validFiles = multipartFile.stream()
                    .filter(multipartFiles -> !multipartFiles.isEmpty())
                    .collect(Collectors.toList());

            if (!validFiles.isEmpty()) {
                List<BoardImage> boardImageList = saveBoardImages(validFiles, board);
                board.setBoardImage(boardImageList);
            }
        }

        boardRepository.save(board);

        return new BoardResponseDTO(board, null);
    }

    // 게시글 이미지 저장
    @Transactional
    public List<BoardImage> saveBoardImages(List<MultipartFile> multipartFiles, Board board) {

        List<BoardImage> boardImages = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            BoardImage image = saveImageBo(multipartFile, board);
            boardImages.add(image);
        }

        return boardImages;
    }

    @Transactional
    public BoardImage saveImageBo(MultipartFile multipartFile, Board board) {

        String originalName = multipartFile.getOriginalFilename();
        BoardImage boardImage = new BoardImage(originalName);
        boardImage.setBoard(board);

        String filename = boardImage.getStoredName();

        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getInputStream().available());

            amazonS3Client.putObject(bucketName, filename, multipartFile.getInputStream(), objectMetadata);
            String accessUrl = amazonS3Client.getUrl(bucketName, filename).toString();
            boardImage.setAccessUrl(accessUrl);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        boardImageRepository.save(boardImage);

        return boardImage;
    }


    //게시글 최신글 조회
    @Transactional(readOnly = true)
    public List<BoardResponseDTO> getAllBoards() {

        return boardRepository.findAll().stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BoardResponseDTO getBoardById(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        List<Comment> comments = commentRepository.findByBoard_BoardId(boardId);
        List<CommentResponseDTO> commentDTOS = comments.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());

        return new BoardResponseDTO(board, commentDTOS);
    }

    @Transactional
    public BoardResponseDTO updateBoard(User user, Long boardId, BoardRequestDTO boardRequestDTO,
                                        List<MultipartFile> multipartFile) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        if (!board.getUser().getUserId().equals(user.getUserId())) {
            throw new CustomException(ErrorCode.FORBIDDEN_WORK);
        }

        validation(boardRequestDTO);

        board.setTitle(boardRequestDTO.getTitle());
        board.setContent(boardRequestDTO.getContent());
        board.setModifiedAt(LocalDateTime.now());

        if (multipartFile != null && !multipartFile.isEmpty()) {
            List<MultipartFile> validFiles = multipartFile.stream()
                    .filter(multipartFiles -> !multipartFiles.isEmpty())
                    .collect(Collectors.toList());

            if (!validFiles.isEmpty()) {
                List<BoardImage> existingImages = board.getBoardImage();
                List<BoardImage> newImages = saveBoardImages(validFiles, board);
                existingImages.addAll(newImages);
                board.setBoardImage(existingImages);
            }
        }

        boardRepository.save(board);

        List<Comment> comments = commentRepository.findByBoard_BoardId(boardId);
        List<CommentResponseDTO> commentDTOS = comments.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());

        return new BoardResponseDTO(board, commentDTOS);
    }

    @Transactional
    public void deleteBoard(User user, Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        if (!board.getUser().getUserId().equals(user.getUserId()) && !board.getUser().getIsAdmin()) {
            throw new CustomException(ErrorCode.FORBIDDEN_WORK);
        }

        for (BoardImage boardImage : board.getBoardImage()) {

            DeleteObjectRequest request = new DeleteObjectRequest(bucketName, boardImage.getStoredName());
            amazonS3Client.deleteObject(request); // S3에서 이미지 삭제
        }

        boardRepository.delete(board);
    }

    //s3삭제 어떤걸로 변수를 받아올지 상의
/*    @Transactional
    public void deleteS3Image(String filename) {

        DeleteObjectRequest request = new DeleteObjectRequest(bucketName, filename);
        amazonS3Client.deleteObject(request); // S3에서 이미지 삭제

        boardImageRepository.delete(boardImage);

    }*/

    @Transactional
    public List<BoardResponseDTO> checkMyBoards(Long userId) {

        return boardRepository.findByUserId(userId).stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void boardLike(User user, Long boardId) {

        Board board = boardRepository.findById(boardId).
                orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BOARD));

        if (boardLikeRepository.existsByUserIdAndBoard_BoardId(user.getUserId(), boardId)) {
            BoardLike boardLike = boardLikeRepository.findByUserIdAndBoard_BoardId(user.getUserId(), boardId);
            boardLikeRepository.delete(boardLike);
            board.setBoardLike(boardLikeRepository.countByBoard_BoardId(boardId));
        } else {
            BoardLike newLike = new BoardLike(user, board);
            boardLikeRepository.save(newLike);
            board.setBoardLike(boardLikeRepository.countByBoard_BoardId(boardId));
        }
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDTO> likeBoardList() {
        return boardRepository.findTopLikedBoards().stream()
                .map(BoardResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void validation(BoardRequestDTO boardRequestDTO) {

        if (boardRequestDTO.getTitle().isEmpty()) {
            throw new CustomException(ErrorCode.TITLE_REQUIRED);
        }
        if (boardRequestDTO.getContent().isEmpty()) {
            throw new CustomException(ErrorCode.CONTENT_REQUIRED);
        }
        if (boardRequestDTO.getTitle().length() > 100) {
            throw new CustomException(ErrorCode.TITLE_TOO_LONG);
        }
        if (boardRequestDTO.getContent().length() > 1000) {
            throw new CustomException(ErrorCode.CONTENT_TOO_LONG);
        }
    }

}

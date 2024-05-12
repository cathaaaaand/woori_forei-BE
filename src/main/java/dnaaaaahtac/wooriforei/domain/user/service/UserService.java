package dnaaaaahtac.wooriforei.domain.user.service;

import dnaaaaahtac.wooriforei.domain.board.repository.BoardRepository;
import dnaaaaahtac.wooriforei.domain.comment.repository.CommentRepository;
import dnaaaaahtac.wooriforei.domain.scheduler.repository.SchedulerMemberRepository;
import dnaaaaahtac.wooriforei.domain.user.dto.PasswordUpdateRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.ProfileRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.ProfileResponseDTO;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SchedulerMemberRepository schedulerMemberRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileResponseDTO getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        // SchedulerMemberRepository에서 스케줄러 아이디와 스케줄러 이름을 조회
        List<ProfileResponseDTO.SchedulerDTO> schedulers = schedulerMemberRepository.findSchedulerInfoByUserId(userId).stream()
                .map(result -> ProfileResponseDTO.SchedulerDTO.builder()
                        .schedulerId((Long) result[0])
                        .schedulerName((String) result[1])
                        .build())
                .collect(Collectors.toList());

        List<ProfileResponseDTO.BoardDTO> boards = boardRepository.findByUserId(userId).stream()
                .map(board -> ProfileResponseDTO.BoardDTO.builder()
                        .boardId(board.getBoardId())
                        .boardTitle(board.getTitle())
                        .build())
                .collect(Collectors.toList());

        List<ProfileResponseDTO.CommentDTO> comments = commentRepository.findByUser_UserId(userId).stream()
                .map(comment -> ProfileResponseDTO.CommentDTO.builder()
                        .commentId(comment.getCommentId())
                        .commentContent(comment.getContent())
                        .build())
                .collect(Collectors.toList());

        return ProfileResponseDTO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .description(user.getDescription())
                .mbti(user.getMbti())
                .birthday(user.getBirthday())
                .nation(user.getNation())
                .schedulers(schedulers)
                .boards(boards)
                .comments(comments)
                .isAdmin(user.getIsAdmin())
                .image(user.getImage())
                .build();
    }


    @Transactional
    public void updateProfile(Long userId, ProfileRequestDTO profileRequestDTO) {
        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        if (!newUser.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS);
        }

        newUser.setUsername(profileRequestDTO.getUsername());
        newUser.setNickname(profileRequestDTO.getNickname());
        newUser.setEmail(profileRequestDTO.getEmail());
        newUser.setDescription(profileRequestDTO.getDescription());

        newUser.setMbti(profileRequestDTO.getMbti());
        newUser.setBirthday(profileRequestDTO.getBirthday());
        newUser.setNation(profileRequestDTO.getNation());

        userRepository.save(newUser);
    }

    @Transactional
    public void updatePassword(Long userId, PasswordUpdateRequestDTO passwordUpdateRequestDTO) {
        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        if (!passwordEncoder.matches(passwordUpdateRequestDTO.getPassword(), newUser.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_USER_PASSWORD);
        }

        if (!passwordUpdateRequestDTO.getUpdatePassword().equals(passwordUpdateRequestDTO.getCheckUpdatePassword())) {
            throw new CustomException(ErrorCode.PASSWORD_CONFIRMATION_FAILED);
        }

        if (!newUser.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS);
        }

        newUser.setPassword(passwordEncoder.encode(passwordUpdateRequestDTO.getUpdatePassword()));
        userRepository.save(newUser);
    }

    @Transactional
    public void deleteUser(Long userId, String password) {
        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        if (!passwordEncoder.matches(password, newUser.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_USER_PASSWORD);
        }

        if (!newUser.getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER_ACCESS);
        }

        userRepository.delete(newUser);
    }
}

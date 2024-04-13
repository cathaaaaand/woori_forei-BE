package dnaaaaahtac.wooriforei.domain.user.service;

import dnaaaaahtac.wooriforei.domain.user.dto.PasswordUpdateRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.UserProfileResponseDTO;
import dnaaaaahtac.wooriforei.domain.user.dto.UserProfileUpdateRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileResponseDTO getUserProfile(Long userId) {

        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        return UserProfileResponseDTO.builder()
                .userId(Long.valueOf(newUser.getUserId()))
                .username(newUser.getUsername())
                .nickname(newUser.getNickname())
                .email(newUser.getUserEmail())
                .introduction(newUser.getIntroduction())
                .mbti(newUser.getMbti())
                .birthday(newUser.getBirthday())
                .nation(newUser.getNation())
                .schedulerId(null)  // 미구현 필드, null로 설정
                .boardId(null)      // 미구현 필드, null로 설정
                .commentId(null)    // 미구현 필드, null로 설정
                .image(newUser.getImage())
                .build();
    }

    @Transactional
    public void updateUserProfile(Long userId, UserProfileUpdateRequestDTO profileUpdate) {

        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        newUser.setUsername(profileUpdate.getUsername());
        newUser.setNickname(profileUpdate.getNickname());
        newUser.setUserEmail(profileUpdate.getEmail());
        newUser.setIntroduction(profileUpdate.getDescription());
        newUser.setMbti(profileUpdate.getMbti());
        newUser.setBirthday(profileUpdate.getBirthday());
        newUser.setNation(profileUpdate.getNation());
        newUser.setImage(profileUpdate.getImage());

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

        newUser.setPassword(passwordEncoder.encode(passwordUpdateRequestDTO.getUpdatePassword()));
        userRepository.save(newUser);
    }
}

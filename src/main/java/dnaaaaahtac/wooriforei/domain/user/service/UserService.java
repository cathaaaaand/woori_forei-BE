package dnaaaaahtac.wooriforei.domain.user.service;

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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileResponseDTO getProfile(Long userId) {
        User newUser = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION));

        return ProfileResponseDTO.builder()
                .userId(newUser.getUserId())
                .username(newUser.getUsername())
                .nickname(newUser.getNickname())
                .email(newUser.getEmail())
                .introduction(newUser.getIntroduction())
                .mbti(newUser.getMbti())
                .birthday(newUser.getBirthday())
                .nation(newUser.getNation())
                .schedulerId(null)
                .boardId(null)
                .commentId(null)
                .isAdmin(newUser.getIsAdmin())
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
        newUser.setIntroduction(profileRequestDTO.getInformation());
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

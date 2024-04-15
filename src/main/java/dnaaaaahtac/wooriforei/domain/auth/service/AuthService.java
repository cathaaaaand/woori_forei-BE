package dnaaaaahtac.wooriforei.domain.auth.service;

import dnaaaaahtac.wooriforei.domain.auth.dto.LoginRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.LoginResponseDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.entity.EmailVerification;
import dnaaaaahtac.wooriforei.domain.auth.repository.EmailVerificationRepository;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.Jwt.JwtUtil;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${admin_secret_code}")
    private String adminSecretCode;

    public void register(RegisterRequestDTO registerRequestDTO) {

        if (!registerRequestDTO.getPassword().equals(registerRequestDTO.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_CONFIRMATION_FAILED);
        }

        userRepository.findByEmail(registerRequestDTO.getEmail()).ifPresent(user -> {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        });

        userRepository.findByNickname(registerRequestDTO.getNickname()).ifPresent(user -> {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        });

        if (Boolean.FALSE.equals(registerRequestDTO.getIsAgreed())) {
            throw new CustomException(ErrorCode.AGREEMENT_NOT_ACCEPTED);
        }

        if (registerRequestDTO.getSecretCode() != null) {
            if (adminSecretCode.equals(registerRequestDTO.getSecretCode())) {
                registerRequestDTO.setAdmin(true);
            } else {
                throw new CustomException(ErrorCode.INVALID_SECRET_CODE);
            }
        }

        EmailVerification verification = emailVerificationRepository.findByEmailAndVerified(registerRequestDTO.getEmail(), true)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_VERIFIED));

        User newUser = new User();
        newUser.setUsername(registerRequestDTO.getUsername());
        newUser.setNickname(registerRequestDTO.getNickname());
        newUser.setEmail(registerRequestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        newUser.setMbti(registerRequestDTO.getMbti());
        newUser.setBirthday(registerRequestDTO.getBirthday());
        newUser.setNation(registerRequestDTO.getNation());
        newUser.setAgreed(registerRequestDTO.getIsAgreed());
        newUser.setAdmin(registerRequestDTO.getIsAdmin());

        userRepository.save(newUser);

        verification.setUserId(newUser);
        emailVerificationRepository.save(verification);
    }

    public LoginResponseDTO login(LoginRequestDTO requestDTO) {

        User newUser = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new CustomException((ErrorCode.NOT_FOUND_USER_EXCEPTION)));

        if (!passwordEncoder.matches(requestDTO.getPassword(), newUser.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return LoginResponseDTO.builder()
                .userId(newUser.getUserId())
                .username(newUser.getUsername())
                .nickname(newUser.getNickname())
                .email(newUser.getEmail())
                .phoneNumber(newUser.getPhoneNumber())
                .introduction(newUser.getIntroduction())
                .mbti(newUser.getMbti())
                .birthday(newUser.getBirthday())
                .nation(newUser.getNation())
                .image(newUser.getImage())
                .isAdmin(newUser.getIsAdmin())
                .isAgreed(newUser.getIsAgreed())
                .build();
    }
}

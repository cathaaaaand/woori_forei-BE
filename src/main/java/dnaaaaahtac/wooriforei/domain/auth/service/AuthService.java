package dnaaaaahtac.wooriforei.domain.auth.service;

import dnaaaaahtac.wooriforei.domain.admin.entity.Admin;
import dnaaaaahtac.wooriforei.domain.admin.repository.AdminRepository;
import dnaaaaahtac.wooriforei.domain.auth.dto.*;
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
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${admin_secret_code}")
    private String adminSecretCode;

    public void registerUser(RegisterUserRequestDTO requestDTO) {

        if (!requestDTO.getPassword().equals(requestDTO.getCheckPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_CONFIRMATION_FAILED);
        }

        userRepository.findByUserEmail(requestDTO.getEmail()).ifPresent(user -> {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        });

        userRepository.findByNickname(requestDTO.getNickname()).ifPresent(user -> {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        });

        if (Boolean.FALSE.equals(requestDTO.getIsAgreed())) {
            throw new CustomException(ErrorCode.AGREEMENT_NOT_ACCEPTED);
        }

        User newUser = new User();
        newUser.setUsername(requestDTO.getUsername());
        newUser.setNickname(requestDTO.getNickname());
        newUser.setUserEmail(requestDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        newUser.setMbti(requestDTO.getMbti());
        newUser.setBirthday(requestDTO.getBirthday());
        newUser.setNation(requestDTO.getNation());
        newUser.setAgreed(requestDTO.getIsAgreed());

        userRepository.save(newUser);
    }

    public void registerAdmin(RegisterAdminRequestDTO requestDTO) {

        if (!adminSecretCode.equals(requestDTO.getSecretCode())) {
            throw new CustomException(ErrorCode.INVALID_SECRET_CODE);
        }

        adminRepository.findByAdminEmail(requestDTO.getEmail()).ifPresent(user -> {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        });

        if (Boolean.FALSE.equals(requestDTO.getIsAgreed())) {
            throw new CustomException(ErrorCode.AGREEMENT_NOT_ACCEPTED);
        }

        Admin newAdmin = new Admin();
        newAdmin.setAdminEmail(requestDTO.getEmail());
        newAdmin.setAdminName(requestDTO.getAdminName());
        newAdmin.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        newAdmin.setPhoneNumber(requestDTO.getPhoneNumber());
        newAdmin.setAgreed(requestDTO.getIsAgreed());

        adminRepository.save(newAdmin);
    }

    public LoginUserResponseDTO loginUser(LoginRequestDTO requestDTO) {

        User newUser = userRepository.findByUserEmail(requestDTO.getEmail())
                .orElseThrow(() -> new CustomException((ErrorCode.NOT_FOUND_USER_EXCEPTION)));

        if (!passwordEncoder.matches(requestDTO.getPassword(), newUser.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return LoginUserResponseDTO.builder()
                .userId(Long.valueOf(newUser.getUserId()))
                .username(newUser.getUsername())
                .nickname(newUser.getNickname())
                .email(newUser.getUserEmail())
                .introduction(newUser.getIntroduction())
                .mbti(newUser.getMbti())
                .birthday(newUser.getBirthday())
                .nation(newUser.getNation())
                .build();
    }

    public LoginAdminResponseDTO loginAdmin(LoginRequestDTO requestDTO) {

        Admin newAdmin = adminRepository.findByAdminEmail(requestDTO.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ADMIN));

        if (!passwordEncoder.matches(requestDTO.getPassword(), newAdmin.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return LoginAdminResponseDTO.builder()
                .adminId(newAdmin.getAdminId())
                .adminName(newAdmin.getAdminName())
                .adminEmail(newAdmin.getAdminEmail())
                .phoneNumber(newAdmin.getPhoneNumber())
                .build();
    }
}

package dnaaaaahtac.wooriforei.domain.auth.service;

import dnaaaaahtac.wooriforei.domain.admin.entity.Admin;
import dnaaaaahtac.wooriforei.domain.admin.repository.AdminRepository;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterAdminRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterUserRequestDTO;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        newAdmin.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        newAdmin.setPhone(requestDTO.getPhoneNumber());
        newAdmin.setAgreed(requestDTO.getIsAgreed());

        adminRepository.save(newAdmin);
    }
}

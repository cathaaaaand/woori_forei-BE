package dnaaaaahtac.wooriforei.domain.auth.service;

import dnaaaaahtac.wooriforei.domain.auth.dto.GoogleLoginResponseDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.LoginRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.LoginResponseDTO;
import dnaaaaahtac.wooriforei.domain.auth.dto.RegisterRequestDTO;
import dnaaaaahtac.wooriforei.domain.auth.entity.EmailVerification;
import dnaaaaahtac.wooriforei.domain.auth.entity.SocialLogin;
import dnaaaaahtac.wooriforei.domain.auth.repository.EmailVerificationRepository;
import dnaaaaahtac.wooriforei.domain.auth.repository.SocialLoginRepository;
import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import dnaaaaahtac.wooriforei.global.Jwt.JwtUtil;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final SocialLoginRepository socialLoginRepository;
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

        if (registerRequestDTO.getIsAdmin() && registerRequestDTO.getSecretCode() == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_SECRET_CODE);
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
                .isAdmin(newUser.getIsAdmin())
                .isAgreed(newUser.getIsAgreed())
                .build();
    }

    public GoogleLoginResponseDTO googleLogin(String code) {

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("google");
        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, code, null, null);
        OAuth2UserRequest userRequest = new OAuth2UserRequest(clientRegistration, accessToken);
        DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email).orElseGet(() -> registerNewUser(oAuth2User));
        SocialLogin socialLogin = socialLoginRepository.findByExternalId(oAuth2User.getName()).orElseGet(() -> registerNewSocialLogin(user, oAuth2User));

        String token = jwtUtil.createToken(String.valueOf(user.getId()));

        return new GoogleLoginResponseDTO(user.getId(), user.getUsername(), token);
    }


    private User registerNewUser(OAuth2User oAuth2User) {

        User newUser = new User();
        newUser.setEmail(oAuth2User.getAttribute("email"));
        newUser.setUsername(oAuth2User.getAttribute("name"));
        newUser.setPassword(passwordEncoder.encode("defaultPassword"));
        newUser.setAdmin(false);
        userRepository.save(newUser);

        return newUser;
    }

    private SocialLogin registerNewSocialLogin(User user, OAuth2User oAuth2User) {

        SocialLogin newSocialLogin = new SocialLogin();
        newSocialLogin.setUser(user);
        newSocialLogin.setExternalId(oAuth2User.getName());
        newSocialLogin.setSocialType("google");
        socialLoginRepository.save(newSocialLogin);

        return newSocialLogin;
    }
}

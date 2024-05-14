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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final SocialLoginRepository socialLoginRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${admin_secret_code}")
    private String adminSecretCode;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

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

        if (!((registerRequestDTO.getSecretCode().isEmpty()) || registerRequestDTO.getSecretCode().isBlank())) {
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
        newUser.setDescription(registerRequestDTO.getIntroduction());
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
                .introduction(newUser.getDescription())
                .mbti(newUser.getMbti())
                .birthday(newUser.getBirthday())
                .nation(newUser.getNation())
                .isAdmin(newUser.getIsAdmin())
                .isAgreed(newUser.getIsAgreed())
                .build();
    }

    public GoogleLoginResponseDTO googleLogin(String code) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("code", code);
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("redirect_uri", redirectUri);
            params.add("grant_type", "authorization_code");

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

            ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("google");
            if (clientRegistration == null) {
                throw new CustomException(ErrorCode.NOT_FOUND_CLIENT_REGISTRATION);
            }

            String tokenUrl = "https://oauth2.googleapis.com/token";
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, requestEntity, String.class);

            // JSON 파싱을 통해 액세스 토큰을 가져옴
            JSONObject jsonObject = new JSONObject(response.getBody());
            String accessTokenValue = jsonObject.getString("access_token").trim();

            OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, accessTokenValue, null, null);
            OAuth2UserRequest userRequest = new OAuth2UserRequest(clientRegistration, accessToken);
            DefaultOAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

            User user = userRepository.findByEmail(oAuth2User.getAttribute("email"))
                    .orElseGet(() -> registerNewUser(oAuth2User));
            registerNewSocialLogin(user, oAuth2User); // Ensure social login info is also saved
            String jwtToken = jwtUtil.createToken(String.valueOf(user.getId()));

            return new GoogleLoginResponseDTO(user.getId(), user.getUsername(), jwtToken);
        } catch (Exception e) {
            System.out.println("Error during Google login: " + e);
            throw new RuntimeException("Login failed", e);
        }
    }

    private User registerNewUser(OAuth2User oAuth2User) {
        User newUser = new User();
        newUser.setEmail(oAuth2User.getAttribute("email"));
        newUser.setUsername(oAuth2User.getAttribute("name"));
        newUser.setNickname(oAuth2User.getAttribute("nickname"));
        newUser.setPassword(passwordEncoder.encode("defaultPassword"));
        newUser.setAdmin(false);
        userRepository.save(newUser);
        return newUser;
    }

    private void registerNewSocialLogin(User user, OAuth2User oAuth2User) {
        SocialLogin newSocialLogin = new SocialLogin();
        newSocialLogin.setUser(user);
        newSocialLogin.setExternalId(oAuth2User.getName());
        newSocialLogin.setSocialType("google");
        socialLoginRepository.save(newSocialLogin);
    }
}

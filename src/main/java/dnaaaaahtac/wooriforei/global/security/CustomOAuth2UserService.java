package dnaaaaahtac.wooriforei.global.security;

import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        try {
            OAuth2User oAuth2User = super.loadUser(userRequest);
            return processOAuth2User(oAuth2User);
        } catch (OAuth2AuthenticationException e) {
            throw e; // 이미 OAuth2Error를 포함하고 있는 경우 그대로 throw
        } catch (Exception ex) {
            // OAuth2Error를 생성하여 OAuth2AuthenticationException에 전달
            OAuth2Error oAuth2Error = new OAuth2Error(
                    "error_processing_oauth_user",
                    "An error occurred while processing OAuth2 user", null);
            throw new OAuth2AuthenticationException(oAuth2Error, ex.getMessage(), ex);
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User) {

        String email = oAuth2User.getAttribute("email");
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> registerNewUser(oAuth2User));

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if (user.getIsAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new DefaultOAuth2User(
                authorities,
                oAuth2User.getAttributes(),
                "email"  // 네임 어트리뷰트 키
        );
    }

    private User registerNewUser(OAuth2User oAuth2User) {

        User newUser = new User();
        newUser.setEmail(oAuth2User.getAttribute("email"));
        newUser.setUsername(oAuth2User.getAttribute("name"));
        newUser.setPassword(""); // 비밀번호는 OAuth 로그인에서는 불필요하거나 별도 처리가 필요
        newUser.setAdmin(false); // 기본적으로 사용자는 일반 사용자로 등록
        userRepository.save(newUser);

        return newUser;
    }
}
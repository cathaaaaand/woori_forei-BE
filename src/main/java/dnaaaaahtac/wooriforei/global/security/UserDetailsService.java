package dnaaaaahtac.wooriforei.global.security;

import dnaaaaahtac.wooriforei.domain.user.entity.User;
import dnaaaaahtac.wooriforei.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // userId를 Long 타입으로 파싱합니다. 유효하지 않은 포맷이면 예외를 발생시킵니다.
        Long id;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID format: " + userId, e);
        }

        // userId를 사용하여 사용자를 찾습니다.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userId: " + id));

        // UserDetails를 구현한 클래스의 인스턴스를 반환합니다.
        return new UserDetailsImpl(user);
    }
}

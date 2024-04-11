package dnaaaaahtac.wooriforei.domain.user.repository;

import dnaaaaahtac.wooriforei.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUserEmail(String email);

    Optional<User> findByNickname(String nickname);
}

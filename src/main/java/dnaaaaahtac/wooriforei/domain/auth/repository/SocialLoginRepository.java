package dnaaaaahtac.wooriforei.domain.auth.repository;

import dnaaaaahtac.wooriforei.domain.auth.entity.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {

    Optional<SocialLogin> findByExternalId(String externalId);
}

package dnaaaaahtac.wooriforei.domain.admin.repository;

import dnaaaaahtac.wooriforei.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByAdminEmail(String email);
}

package dnaaaaahtac.wooriforei.domain.faq.repository;

import dnaaaaahtac.wooriforei.domain.faq.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq, Long> {
}

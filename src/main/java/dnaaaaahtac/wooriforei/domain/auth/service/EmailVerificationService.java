package dnaaaaahtac.wooriforei.domain.auth.service;

import dnaaaaahtac.wooriforei.domain.auth.entity.EmailVerification;
import dnaaaaahtac.wooriforei.domain.auth.repository.EmailVerificationRepository;
import dnaaaaahtac.wooriforei.global.exception.CustomException;
import dnaaaaahtac.wooriforei.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String email) {
        String code = generateVerificationCode();
        LocalDateTime now = LocalDateTime.now();

        EmailVerification verification = emailVerificationRepository.findByEmail(email)
                .map(v -> {
                    if (v.getSendAttempts() >= 3) {
                        throw new CustomException(ErrorCode.EMAIL_SEND_LIMIT_EXCEEDED);
                    }
                    v.setVerificationCode(code);
                    v.setCreatedAt(now);
                    v.setExpiresAt(now.plusMinutes(3));
                    v.setVerified(false);
                    v.incrementSendAttempts();
                    return v;
                })
                .orElseGet(() -> {
                    EmailVerification newVerification = new EmailVerification();
                    newVerification.setEmail(email);
                    newVerification.setVerificationCode(code);
                    newVerification.setCreatedAt(now);
                    newVerification.setExpiresAt(now.plusMinutes(3));
                    newVerification.setVerified(false);
                    newVerification.incrementSendAttempts();
                    return newVerification;
                });

        emailVerificationRepository.save(verification);
        sendEmail(email, code);
    }

    private String generateVerificationCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    private void sendEmail(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("이메일 인증");
        message.setText("인증 코드: " + code);
        mailSender.send(message);
    }

    public boolean verifyEmail(String email, String verificationCode) {
        EmailVerification verification = emailVerificationRepository.findByEmailAndVerificationCode(email, verificationCode)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_VERIFICATION_NOT_FOUND));

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new CustomException(ErrorCode.EXPIRED_VERIFICATION_CODE);
        }

        verification.setVerified(true);
        emailVerificationRepository.save(verification);

        return true;
    }
}

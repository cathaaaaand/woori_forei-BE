package dnaaaaahtac.wooriforei.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Admin
    INVALID_SECRET_CODE(400, "비밀 암호가 올바르지 않습니다."),

    // Auth
    PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
    EMAIL_ALREADY_EXISTS(400, "이미 사용중인 이메일입니다."),
    NICKNAME_ALREADY_EXISTS(400, "이미 사용중인 닉네임입니다."),
    PASSWORD_CONFIRMATION_FAILED(400, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    AGREEMENT_NOT_ACCEPTED(400, "약관에 동의하지 않았습니다."),

    // Board

    // Comment

    // Faq

    // Scheduler

    // User
    NOT_FOUND_USER_EXCEPTION(404, "해당 유저가 존재하지 않습니다."),

    // Common
    INVALID_JWT_TOKEN(401, "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(401, "JWT 토큰이 만료되었습니다."),
    UNSUPPORTED_JWT_TOKEN(401, "지원되지 않는 JWT 토큰입니다.");

    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}

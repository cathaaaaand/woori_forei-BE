package dnaaaaahtac.wooriforei.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Auth

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

    ErrorCode(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}

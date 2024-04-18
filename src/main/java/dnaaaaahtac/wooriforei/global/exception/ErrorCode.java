package dnaaaaahtac.wooriforei.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // Admin
    INVALID_SECRET_CODE(400, "비밀 암호가 올바르지 않습니다."),
    NOT_FOUND_ADMIN(404, "해당 관리자가 존재하지 않습니다."),

    // Auth
    PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
    EMAIL_ALREADY_EXISTS(400, "이미 사용중인 이메일입니다."),
    NICKNAME_ALREADY_EXISTS(400, "이미 사용중인 닉네임입니다."),
    PASSWORD_CONFIRMATION_FAILED(400, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    AGREEMENT_NOT_ACCEPTED(400, "약관에 동의하지 않았습니다."),
    INVALID_PASSWORD(401, "이메일 또는 비밀번호가 유효하지 않습니다."),
    EMAIL_SEND_FAILED(500, "이메일 발송에 실패했습니다."),
    EMAIL_VERIFICATION_NOT_FOUND(404, "요청된 이메일 인증 정보를 찾을 수 없습니다."),
    INVALID_VERIFICATION_CODE(400, "제공된 인증 코드가 잘못되었습니다."),
    EXPIRED_VERIFICATION_CODE(410, "인증 코드가 만료되었습니다."),
    EMAIL_SEND_LIMIT_EXCEEDED(429, "인증 이메일 전송 횟수가 초과되었습니다."),
    EMAIL_NOT_VERIFIED(400, "이메일이 인증되지 않았습니다."),

    // Board

    // Comment

    // Faq

    // Scheduler
    INVALID_START_DATE(400, "시작일이 현재 시간 이전일 수 없습니다."),
    INVALID_END_DATE(400, "종료일이 시작일 이전일 수 없습니다."),
    NOT_FOUND_SCHEDULER(404, "요청한 스케줄러를 찾을 수 없습니다."),
    NOT_FOUND_ACTIVITY(404, "요청한 문화 체험을 찾을 수 없습니다."),
    NOT_FOUND_HOTEL(404, "요청한 호텔을 찾을 수 없습니다."),
    NOT_FOUND_INFORMATION(404, "요청한 안내소를 찾을 수 없습니다."),
    NOT_FOUND_LANDMARK(404, "요청한 명소를 찾을 수 없습니다."),
    NOT_FOUND_RESTAURANT(404, "요청한 맛집을 찾을 수 없습니다."),
    NOT_FOUND_SEOUL_GOODS(404, "요청한 기념품판매소를 찾을 수 없습니다."),
    INVALID_TIME_OVERLAP(409, "요청한 시간이 기존의 일정과 겹칩니다."),

    //OpenAPI
    NOT_FOUND_DATA(404, "데이터가 존재하지 않습니다."),

    // User
    NOT_FOUND_USER_EXCEPTION(404, "해당 유저가 존재하지 않습니다."),
    INVALID_USER_PASSWORD(401, "비밀번호가 올바르지 않습니다."),
    UNAUTHORIZED_USER_ACCESS(403, "접근 권한이 없습니다."),

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

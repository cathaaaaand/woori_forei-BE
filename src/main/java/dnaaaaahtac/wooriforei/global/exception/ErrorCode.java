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
    NOT_FOUND_USER_EXCEPTION(404, "해당 유저가 존재하지 않습니다.");

    // Common

    private final int statusCode;

    private final String message;

    ErrorCode(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}

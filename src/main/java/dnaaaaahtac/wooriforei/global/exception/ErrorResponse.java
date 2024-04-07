package dnaaaaahtac.wooriforei.global.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ErrorResponse {

    private int statusCode;

    private String message;

    public static ErrorResponse of(HttpStatus statusCode, String message){

        return ErrorResponse.builder()
                .statusCode(statusCode.value())
                .message(message)
                .build();
    }
}

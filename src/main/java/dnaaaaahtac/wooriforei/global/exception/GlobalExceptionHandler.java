package dnaaaaahtac.wooriforei.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handlerCustomException(CustomException customException) {

        log.error(customException.getMessage());

        HttpStatus httpStatus = HttpStatus.valueOf(customException.getStatusCode());
        ErrorResponse errorResponse = ErrorResponse.of(customException.getMessage());

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}

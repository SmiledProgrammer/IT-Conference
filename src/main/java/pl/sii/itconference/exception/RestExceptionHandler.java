package pl.sii.itconference.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.sii.itconference.utils.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex) {
        return createErrorResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ApiError> handleDuplicateUsername(DuplicateUsernameException ex) {
        return createErrorResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        return createErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        StringBuilder msgBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            String[] path = violation.getPropertyPath().toString().split("\\.");
            String propertyName = StringUtils.capitalize(path[path.length-1]);
            String violationMsg = propertyName + " " + violation.getMessage();
            if (!msgBuilder.isEmpty()) {
                msgBuilder.append("; ");
            }
            msgBuilder.append(violationMsg);
        }
        String msg = msgBuilder.append(".").toString();
        log.error("{} - {}", ex.getClass().getName(), msg);
        return createErrorResponseEntity(HttpStatus.BAD_REQUEST, msg);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleAnyException(RuntimeException ex) {
        log.error("{} - {}", ex.getClass().getName(), ex.getMessage());
        return createErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error.");
    }

    private ResponseEntity<ApiError> createErrorResponseEntity(HttpStatus status, String msg) {
        return new ResponseEntity<>(new ApiError(status, msg), status);
    }
}

package it.tdgc.turnstile.util;

import it.tdgc.turnstile.exception.BadgeAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


        @ExceptionHandler(BadgeAlreadyExistsException.class)
        public ResponseEntity<ApiResponse<String>> handleBadgeAlreadyExistsException(BadgeAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("400", e.getMessage(), null, new Date(), null));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<String>> handleGenericException(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>("500", "An unexpected error occurred.", null, new Date(), null));
        }
    }

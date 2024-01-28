package com.demo.book.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class JwtExceptionHandle {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleSecurityException(Exception ex) {
        ProblemDetail errorDetail = null;
        if (ex instanceof ExpiredJwtException) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("message", ex.getMessage());
            body.put("status", HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }
        return null;
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<Object> handleSecurityException(TokenRefreshException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}

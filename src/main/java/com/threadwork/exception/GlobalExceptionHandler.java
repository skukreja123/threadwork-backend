package com.threadwork.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import java.util.Map;
@Slf4j @RestControllerAdvice
public class GlobalExceptionHandler {
  private static ResponseEntity<Map<String, String>> body(HttpStatus s, String m) { return ResponseEntity.status(s).body(Map.of("error", m)); }
  @ExceptionHandler(ApiException.class) ResponseEntity<Map<String, String>> api(ApiException e) { return body(e.status(), e.getMessage()); }
  @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String, String>> invalid(MethodArgumentNotValidException e) {
    var f = e.getBindingResult().getFieldError();
    return body(HttpStatus.BAD_REQUEST, f == null ? "Invalid request" : f.getField() + ": " + f.getDefaultMessage());
  }
  @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class}) ResponseEntity<Map<String, String>> unreadable(Exception e) { return body(HttpStatus.BAD_REQUEST, "Invalid request"); }
  @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<Map<String, String>> conflict(DataIntegrityViolationException e) { log.warn("Data conflict", e); return body(HttpStatus.CONFLICT, "That data conflicts with existing records"); }
  @ExceptionHandler(NoResourceFoundException.class) ResponseEntity<Map<String, String>> notFound(NoResourceFoundException e) { return body(HttpStatus.NOT_FOUND, "Not found"); }
  @ExceptionHandler(Exception.class) ResponseEntity<Map<String, String>> other(Exception e) { log.error("Unhandled error", e); return body(HttpStatus.INTERNAL_SERVER_ERROR, "Server error"); }
}

package com.threadwork.exception;
import org.springframework.http.HttpStatus;
/** Thrown by services. The handler turns it into {"error": "..."} with this status. */
public class ApiException extends RuntimeException {
  private final HttpStatus status;
  public ApiException(HttpStatus status, String message) { super(message); this.status = status; }
  public HttpStatus status() { return status; }
}

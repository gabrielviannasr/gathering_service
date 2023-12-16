package br.com.gathering.config;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
//        return handleExceptionInternal(ex, ex.getReason(), null, ex.getStatusCode(), request);
//    }
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setStatus(ex.getStatusCode().value());
        errorResponse.setError(ex.getBody().getTitle());
        errorResponse.setMessage(ex.getReason());
        errorResponse.setPath(((ServletWebRequest) request).getRequest().getRequestURI());

        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    // Add other exception handlers if needed
}


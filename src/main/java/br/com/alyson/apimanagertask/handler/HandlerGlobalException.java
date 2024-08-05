package br.com.alyson.apimanagertask.handler;

import br.com.alyson.apimanagertask.domain.exception.BusinessException;
import br.com.alyson.apimanagertask.domain.exception.DatabaseException;
import br.com.alyson.apimanagertask.domain.exception.EntityNotFoundException;
import br.com.alyson.apimanagertask.domain.exception.MyCustomTimeoutException;
import br.com.alyson.apimanagertask.response.ValidationErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerGlobalException extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request){
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest webRequest) throws Exception {
        String errorMessage = ex.getMessage();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleTransactionException(EntityNotFoundException ex, WebRequest webRequest) throws Exception {
        String errorMessage = ex.getMessage();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<?> handleTransactionException(DatabaseException ex, WebRequest webRequest) throws Exception {
        String errorMessage = ex.getMessage();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        ApiProblem apiProblem = ApiProblem.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .title(HttpStatus.FORBIDDEN.getReasonPhrase())
                .type("https://api.com.br/forbidden")
                .detail(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, apiProblem, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<ValidationErrorResponse.Violation> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorResponse.Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setViolations(violations);

        ApiProblem apiProblem = ApiProblem.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .type("https://api.com.br/bad-request")
                .detail("Validation error")
                .violations(errorResponse.getViolations())
                .build();


        return this.handleExceptionInternal(ex, apiProblem, headers, status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<ValidationErrorResponse.Violation> violations = ex.getConstraintViolations().stream()
                .map(violation -> new ValidationErrorResponse.Violation(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());

        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        errorResponse.setViolations(violations);

        ApiProblem apiProblem = ApiProblem.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .title(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .type("https://api.com.br/bad-request")
                .detail("Validation error")
                .build();

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // Tratar exceções genéricas
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiProblem apiProblem = ApiProblem.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .title(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .type("https://api.com.br/internal-server-error")
                .detail(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, apiProblem, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


}

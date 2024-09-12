package com.FlowBanck.exception;

import com.FlowBanck.exception.login.*;
import com.FlowBanck.payload.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

   @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException exception,
                                                                   WebRequest webRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false).replace("uri=",""))
                .build();
        logger.error("Recurso no encontrado: {} - {}", exception.getMessage(), errorResponse.getPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

   @ExceptionHandler(TokenExpiredException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handlerTokenExpiredException(TokenExpiredException exception,WebRequest webRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false).replace("uri=",""))
                .build();
        logger.error("Token expirado o invalido: {} - {}", exception.getMessage(), errorResponse.getPath());
        return  new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handlerInvalidCredentialsException(InvalidCredentialsException exception,WebRequest webRequest){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(exception.getMessage())
                .path(webRequest.getDescription(false).replace("uri=",""))
                .build();
        logger.error("Email o contraseña invalida: {} - {}", exception.getMessage(), errorResponse.getPath());
        return  new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handlerAccountDisabledException(AccountLockedException exception, WebRequest webRequest){
       ErrorResponse errorResponse = ErrorResponse.builder()
               .timestamp(LocalDateTime.now().toString())
               .status(HttpStatus.FORBIDDEN.value())
               .error(HttpStatus.FORBIDDEN.getReasonPhrase())
               .message(exception.getMessage())
               .path(webRequest.getDescription(false).replace("uri=",""))
               .build();
       logger.error("Cuenta bloqueada: {} - {}",exception.getMessage(), errorResponse.getPath());
       return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountDisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handlerAccountDisabledException(AccountDisabledException exception, WebRequest webRequest){
       ErrorResponse errorResponse = ErrorResponse.builder()
               .timestamp(LocalDateTime.now().toString())
               .status(HttpStatus.FORBIDDEN.value())
               .error(HttpStatus.FORBIDDEN.getReasonPhrase())
               .message(exception.getMessage())
               .path(webRequest.getDescription(false).replace("uri=", ""))
               .build();
       logger.error("Cuenta inactiva: {} - {}",exception.getMessage(), errorResponse.getPath());
       return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TooManyFailedLoginAttemptsException.class)
    public ResponseEntity<ErrorResponse> handlerTooManyFailedLoginAttemptsException(TooManyFailedLoginAttemptsException exception,
                                                                                    WebRequest webRequest){

       ErrorResponse errorResponse = ErrorResponse.builder()
               .timestamp(LocalDateTime.now().toString())
               .status(HttpStatus.FORBIDDEN.value())
               .error(HttpStatus.FORBIDDEN.getReasonPhrase())
               .message(exception.getMessage())
               .path(webRequest.getDescription(false).replace("uri=",""))
               .build();
       logger.error("Cuenta bloqueada por varios intentos: {} - {}",exception.getMessage(), errorResponse.getPath());
       return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }









    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}

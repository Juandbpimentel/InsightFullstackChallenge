package br.ufc.quixada.SupplierApplicationInsight.config.infra.security;

import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.*;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    public CustomExceptionHandler(View error) {
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, Object> errorMap = new HashMap<>();
        Map<String, String> invalidArguments = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                invalidArguments.put(error.getField(), error.getDefaultMessage()));

        errorMap.put("errorCode", "invalid-arguments");
        errorMap.put("invalid-arguments", invalidArguments);
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUnexpectedTypeException(
            UnexpectedTypeException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "validation-unexpected-type");
        return errorMap;
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(
            UserNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "user-not-found");
        return errorMap;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUsernameNotFoundException(
            UsernameNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "user-not-found");
        return errorMap;
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleSupplierNotFoundException(
            SupplierNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "supplier-not-found");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SupplyNotFoundException.class)
    public Map<String, String> handleSupplyNotFoundException(
            SupplyNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "supply-not-found");
        return errorMap;
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleAdminAlreadyExistsException(
            AdminAlreadyExistsException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorCode", "admin-already-exists");
        errorMap.put("message", exception.getMessage());
        return errorMap;
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleUserAlreadyExistsException(
            UserAlreadyExistsException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "user-already-exists");
        return errorMap;
    }

    @ExceptionHandler(SupplierAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleSupplierAlreadyExistsException(
            SupplierAlreadyExistsException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "supplier-already-exists");
        return errorMap;
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAuthenticationException(
            AuthenticationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "unauthorized");
        return errorMap;
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleAccessDeniedException(
            AuthenticationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "unauthorized");
        return errorMap;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleException(
            Exception exception) {
        Map<String, String> errorMap = new HashMap<>();
        if (exception.getMessage().contains("Required request body is missing")) {
            errorMap.put("message", "Required request body is missing");
            errorMap.put("errorCode", "missing-body-bad-request");
            return errorMap;
        }
        if (exception.getMessage().contains("No fields to update")) {
            errorMap.put("message", "No fields to update");
            errorMap.put("errorCode", "no-fields-update");
            return errorMap;
        }
        errorMap.put("message", exception.getMessage());
        errorMap.put("errorCode", "internal-server-error");
        errorMap.put("exception", exception.getClass().getName());
        return errorMap;
    }

}
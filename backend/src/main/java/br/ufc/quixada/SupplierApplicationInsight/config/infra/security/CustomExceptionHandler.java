package br.ufc.quixada.SupplierApplicationInsight.config.infra.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.View;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.AdminAlreadyExistsException;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserAlreadyExistsException;
import br.ufc.quixada.SupplierApplicationInsight.types.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler
{
    @SuppressWarnings("unused")
    private final View error;

    public CustomExceptionHandler(View error) {
        this.error = error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,Object> handleInvalidArgument(MethodArgumentNotValidException exception) throws JsonProcessingException {
        Map<String, Object> errorMap=new HashMap<>();
        Map<String,String>invalidArguments=new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error->
        {
            invalidArguments.put(error.getField(),error.getDefaultMessage());
        });

        errorMap.put("errorCode","invalid-arguments");
        errorMap.put("invalid-arguments",invalidArguments);
        errorMap.put("message",exception.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String,String>handleUserNotFoundException(
            UserNotFoundException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        errorMap.put("errorCode","user-not-found");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public Map<String,String>handleUsernameNotFoundException(
            UsernameNotFoundException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        errorMap.put("errorCode","user-not-found");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AdminAlreadyExistsException.class)
    public Map<String,String>handleAdminAlreadyExistsException(
            AdminAlreadyExistsException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("errorCode","admin-already-exists");
        errorMap.put("message",exception.getMessage());
        return errorMap;
    }

    @ResponseStatus( HttpStatus.BAD_REQUEST )
    @ExceptionHandler(UserAlreadyExistsException.class)
    public Map<String,String>handleUserAlreadyExistsException(
            UserAlreadyExistsException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        errorMap.put("errorCode","user-already-exists");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public Map<String,String>handleAuthenticationException(
            AuthenticationException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        errorMap.put("errorCode","unauthorized");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String,String>handleException(
            Exception exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        errorMap.put("errorCode","internal-server-error");
        errorMap.put("exception",exception.getClass().getName());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public Map<String,String>handleAccessDeniedException(
            AuthenticationException exception)
    {
        Map<String,String>errorMap=new HashMap<>();
        errorMap.put("message",exception.getMessage());
        errorMap.put("errorCode","unauthorized");
        return errorMap;
    }

}
package efub.assignment.community.exception;

import efub.assignment.community.global.dto.HttpErrorResponse;
import efub.assignment.community.member.controller.MemberController;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponse handleIllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){

        return HttpErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Illegal Argument")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected HttpErrorResponse handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request){
        return HttpErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Entity Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler({EntityExistsException.class})
    protected HttpErrorResponse handleEntityExistsException(EntityExistsException ex, HttpServletRequest request){
        return HttpErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Entity Already Exists")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

}

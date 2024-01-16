package org.example.exception;

import org.example.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionWrapper> handleNotFountException(NotFoundException ex, HttpServletRequest request) {
        // create a json with ExceptionWrapper and return body with 404 status code

        ExceptionWrapper exceptionWrapper = new ExceptionWrapper();
        exceptionWrapper.setTimestamp(LocalDateTime.now());
        exceptionWrapper.setMessage(ex.getMessage());
        exceptionWrapper.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionWrapper.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionWrapper);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionWrapper> handleValidationError(MethodArgumentNotValidException methodArgumentNotValidException, HttpServletRequest httpServletRequest) {
        ExceptionWrapper exceptionWrapper = new ExceptionWrapper();
        exceptionWrapper.setMessage("Invalid Input(s)");
        exceptionWrapper.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionWrapper.setTimestamp(LocalDateTime.now());
        exceptionWrapper.setPath(httpServletRequest.getRequestURI());

        List<ValidationError> validationErrorList = new ArrayList<>();

        for (ObjectError objectError : methodArgumentNotValidException.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objectError).getField();
            Object rejectedValue = ((FieldError) objectError).getRejectedValue();
            String errorMessage = objectError.getDefaultMessage();

            ValidationError validationError = new ValidationError(fieldName, rejectedValue, errorMessage);
            validationErrorList.add(validationError);
        }

        exceptionWrapper.setValidationErrorList(validationErrorList);
        exceptionWrapper.setErrorCount(validationErrorList.size());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionWrapper);
    }
}

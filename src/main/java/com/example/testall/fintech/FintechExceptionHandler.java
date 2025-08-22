package com.example.testall.fintech;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class FintechExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionAccordingBindings(ex);
    }

    private static ResponseEntity<Object> handleExceptionAccordingBindings(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        List<String> fields = new ArrayList<>();
        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            errors.add(objectError.getObjectName() + ": " + objectError.getDefaultMessage());
        }
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
            fields.add(fieldError.getField());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("message", "Объект " + ex.getBindingResult().getObjectName() + " не соответствует модели");
        map.put("errors", errors);
        map.put("fields", fields);
        return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
    }
}

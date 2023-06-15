package com.blogs_apps_api.globalExceptions;

import com.blogs_apps_api.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
    {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message , false);

        return new  ResponseEntity<ApiResponse>(apiResponse , HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>> handleMethodArgumentNotValidException (MethodArgumentNotValidException ex)
    {
        Map<String,String> respMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
          String fieldName =  ((FieldError) e).getField();
          String defaultMessage = e.getDefaultMessage();
          respMap.put(fieldName,defaultMessage);
        });

        return  new ResponseEntity<Map<String,String>>(respMap ,  HttpStatus.BAD_GATEWAY);

    }
}

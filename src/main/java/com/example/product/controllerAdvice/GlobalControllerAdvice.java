package com.example.product.controllerAdvice;

import com.example.product.dtos.ErrorResponseDto;
import com.example.product.exceptions.InvalidProductIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InvalidProductIdException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidProduct(){
        return new ResponseEntity<>(new ErrorResponseDto("Invalid Product from Global Advice"), HttpStatus.NOT_FOUND);
    }
}

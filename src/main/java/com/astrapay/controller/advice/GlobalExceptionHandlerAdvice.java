package com.astrapay.controller.advice;

import com.astrapay.dto.BaseResponseDto;
import com.astrapay.exception.DataNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDto<List<String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<String> errors = exception.
        getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage
        ).collect(Collectors.toList());
        return BaseResponseDto.build(HttpStatus.BAD_REQUEST, "Invalid field constraint", errors);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<BaseResponseDto<Void>> handleDataNotFoundException(DataNotFoundException exception){
        String error = exception.getMessage();
        return BaseResponseDto.build(HttpStatus.NOT_FOUND, error, null);
    }

}

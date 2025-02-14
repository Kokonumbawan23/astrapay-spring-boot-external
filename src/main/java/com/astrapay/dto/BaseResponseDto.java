package com.astrapay.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This class is used to create a response object that will be returned to the client
 * using JSON. It contains a message and a data object.
 * @param <T> The type of Data Response
 */

@Data
public class BaseResponseDto<T>  {

    private String message;
    private T data;

    /**
     * This method build a ResponseEntity object to return a Rest API response
     * @param status Http status code of the response
     * @param message to be returned to the client
     * @param data that will be returned to the client if present
     * @return ResponseEntity
     * @param <T> The type of Data Response
     */

    public static <T>ResponseEntity<BaseResponseDto<T>> build(HttpStatus status, String message, T data) {
        BaseResponseDto<T> response = new BaseResponseDto<>();
        response.setMessage(message);
        response.setData(data);
        return new ResponseEntity<>(response, status);
    }


}

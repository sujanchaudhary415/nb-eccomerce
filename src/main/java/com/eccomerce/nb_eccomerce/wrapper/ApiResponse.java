package com.eccomerce.nb_eccomerce.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private T data;

    public static <T>ApiResponse<T>success(T data,String message){
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T>ApiResponse<T>success(String message){
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }
}

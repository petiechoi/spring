package com.modim.spring.global.response.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@NoArgsConstructor
@JsonInclude(value =  JsonInclude.Include.NON_NULL)
public class Response<T> {
    private boolean success;
    //private int code;
    private String message;
    private T data;

    @Builder
    public Response(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static Response success() {
        return Response.builder()
                .success(true)
                .message("성공")
                .data(null)
                .build();
    }

    public static Response success(String message){
        return Response.builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }
}

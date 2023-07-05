package com.modim.spring.global.response.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Response<T> {
    private int code;
    private String msg;
    private T data;

}

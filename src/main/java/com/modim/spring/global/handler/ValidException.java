package com.modim.spring.global.handler;

import com.modim.spring.global.response.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@ControllerAdvice
@RestController
public class ValidException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        ArrayList<String> arrayList = new ArrayList<String>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            arrayList.add(fieldError.getDefaultMessage());
        }
        return ResponseEntity.ok(Response.error(arrayList));
    }
}

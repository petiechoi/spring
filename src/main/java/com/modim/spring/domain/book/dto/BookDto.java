package com.modim.spring.domain.book.dto;

import com.modim.spring.domain.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class BookDto {
    private BookDto() throws IllegalArgumentException{
        throw new IllegalArgumentException();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class RequestDto {
        @NotEmpty
        private String title;

        @NotEmpty
        private String author;

        @NotEmpty
        private String publisher;
    }
}

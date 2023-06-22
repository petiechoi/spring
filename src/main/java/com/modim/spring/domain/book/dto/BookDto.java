package com.modim.spring.domain.book.dto;

import com.modim.spring.domain.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
public class BookDto {
    private BookDto() throws IllegalArgumentException{
        throw new IllegalArgumentException();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestDto {
        @NotEmpty
        private String title;

        @NotEmpty
        private String author;

        @NotEmpty
        private String publisher;

        public Book toEntity(){
            return Book.builder()
                    .title(title)
                    .author(author)
                    .publisher(publisher)
                    .build();
        }
    }
}

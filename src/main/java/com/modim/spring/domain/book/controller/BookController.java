package com.modim.spring.domain.book.controller;

import com.modim.spring.domain.book.dto.BookDto.RequestDto;
import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    // 도서 등록
    @PostMapping("/book")
    public Long bookCreate(@Valid @RequestBody RequestDto requestDto) {
        return bookService.bookCreate(requestDto);
    }

    // 도서 삭제
    @DeleteMapping("/book")
    public void bookDelete(@PathVariable("id") Long id){
        bookService.bookDelete(id);
    }

    // 도서 수정
    @PutMapping("/book")
    public Long bookUpdate(@Valid @RequestBody RequestDto requestDto, @PathVariable("id") Long id) {
        return bookService.bookUpdate(requestDto, id);
    }

     //도서 전체 목록 조회(미사용)
//    @GetMapping("/bookLis")
//    public List<Book> bookList(){
//        return bookService.bookList();
//    }

    // 도서 전체 목록 조회(페이징)
    @GetMapping("/books")
    public ResponseEntity bookPage(final Pageable pageable){
        Page<Book> books = bookService.bookPage(pageable);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // 도서 단일 조회
    @GetMapping("/book/{id}")
    public Book bookView(@PathVariable Long id){
        return bookService.bookView(id);
    }
}

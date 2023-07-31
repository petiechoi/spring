package com.modim.spring.domain.book.service;

import com.modim.spring.domain.book.dto.BookDto.RequestDto;
import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Long bookCreate(RequestDto requestDto){
        Book book = requestDto.toEntity();
        return bookRepository.save(book).getId();
    }

    @Transactional
    public void bookDelete(Long id){
        bookRepository.deleteById(id);
    }

    // 영속성 컨텍스트가 유지된 상태이지 때문에 쿼리를 날리지 않아도 수정됨 = 더티체킹?
    @Transactional
    public Long bookUpdate(RequestDto requestDto, Long id) {
        Book book = bookRepository.findById(id).get();
        book.update(requestDto);
        return book.getId();
    }

    public Page<Book> bookList(int page){
        //return bookRepository.findAll(PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC,"id"))).getContent();
        Pageable pageable = PageRequest.of(page, 10);
        return bookRepository.findAll(pageable);

    }

    public List<Book> bookList(){
        return bookRepository.findAll();
    }

    public Book bookView(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("책을 조회할 수 없습니다."));
    }
}

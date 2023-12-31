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

    public Book detail(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("책을 조회할 수 없습니다."));
    }

    @Transactional
    public Long create(RequestDto requestDto){
        Book book = requestDto.toEntity();
        return bookRepository.save(book).getId();
    }

    @Transactional
    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    // 영속성 컨텍스트가 유지된 상태이지 때문에 쿼리를 날리지 않아도 수정됨 = 더티체킹?
    @Transactional
    public Long update(RequestDto requestDto, Long id) {
        Book book = bookRepository.findById(id).get();
        book.update(requestDto);
        return book.getId();
    }

    public Page<Book> list(int page){
        long pageend = bookRepository.countBy()/10;
        if (page < 0 ) page = 0;
        if (page > pageend ) page = (int) pageend;
        Pageable pageable = PageRequest.of(page, 10);
        return bookRepository.findAll(pageable);

    }

    public List<Book> list(){
        return bookRepository.findAll();
    }
}

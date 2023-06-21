package com.modim.spring.domain.borrow.service;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.repository.BookRepository;
import com.modim.spring.domain.borrow.model.Borrow;
import com.modim.spring.domain.borrow.repository.BorrowRepository;
import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.util.CurrentMemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BookRepository bookRepository;
    private final CurrentMemberUtil currentMemberUtil;
    // 대여할수있는 책 조회


    // 책 대여
    @Transactional
    public Long borrowBook(Long id){
        Member member = currentMemberUtil.getCurrentMember();
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("책을 찾을 수 없습니다."));

        if(book.getBorrow() == null){
            Borrow borrow = Borrow.builder()
                    .book(book)
                    .member(member)
                    .build();
            return borrowRepository.save(borrow).getId();
        }
        else throw new RuntimeException("이미 대여중입니다.");
    }

    @Transactional
    public Long borrowApply(Long id){
        Borrow borrow = borrowRepository.findById(id).get();
        borrow.Apply(); // must have transactional annotation
        return borrow.getId();
    }

    @Transactional
    public void borrowDelete(Long id){
        borrowRepository.deleteById(id);
    }

    public List<Borrow> borrowList(){
        return borrowRepository.findAll();
    }
}

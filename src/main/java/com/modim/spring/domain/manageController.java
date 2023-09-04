package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.domain.borrow.model.Borrow;
import com.modim.spring.domain.borrow.service.BorrowService;
import com.modim.spring.domain.file.model.File;
import com.modim.spring.domain.file.service.FileService;
import com.modim.spring.domain.member.model.Member;
import com.modim.spring.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/manage")
@RequiredArgsConstructor
@Controller
public class manageController {
    private final BookService bookService;
    private final MemberService memberService;
    private final BorrowService borrowService;
    private final FileService fileService;

    @GetMapping("/borrows")
    public String borrows(Model model){
        List<Borrow> borrows = borrowService.list();
        model.addAttribute("borrows",borrows);
        return "manage/borrows";
    }

    @GetMapping("/books/form")
    public String bookForm(Model model){
        return "manage/book";      // 생성
    }

    @GetMapping("/books/{id}")
    public String bookUpdate(Model model, @PathVariable(value="id") Long id){
        Book book = bookService.detail(id);
        model.addAttribute("book",book);
        return "manage/book";      // 수정
    }

    @GetMapping("/books")
    public String books_mng(Model model){
        List<Book> books = bookService.list();
        model.addAttribute("books",books);
        return "manage/books_mng";
    }

    @GetMapping("/members")
    public String members(Model model){
        List<Member> members = memberService.memberList();
        model.addAttribute("members", members);
        return "manage/members";
    }

    @GetMapping("/files/upload")
    public String fileUpload(Model model){
        return "manage/file";
    }

    @GetMapping("/files")
    public String files(Model model){
        List<File> files = fileService.list();
        model.addAttribute("files", files);
        return "manage/files";
    }
}

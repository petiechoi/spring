package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.domain.borrow.model.Borrow;
import com.modim.spring.domain.borrow.service.BorrowService;
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

    @GetMapping("/borrow/list")
    public String borrowList(Model model){
        List<Borrow> borrowList = borrowService.borrowList();
        model.addAttribute("borrowList",borrowList);
        return "manage/borrowList";
    }

    @GetMapping("/book/create")
    public String bookCreate(Model model){
        return "manage/bookCU";      // 생성
    }

    @GetMapping("/book/update/{id}")
    public String bookUpdate(Model model, @PathVariable(value="id") Long id){
        Book book = bookService.bookView(id);
        model.addAttribute("book",book);
        return "manage/bookCU";      // 수정
    }

    @GetMapping("/book/list")
    public String bookList(Model model){
        List<Book> bookList = bookService.bookList();
        model.addAttribute("bookList",bookList);
        return "manage/bookList_mng";
    }

    @GetMapping("/member/list")
    public String memberList(Model model){
        List<Member> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "manage/memberList";
    }

    @GetMapping("/file/upload")
    public String fileupload(Model model){
        return "manage/file";
    }

    @GetMapping("/file/list")
    public String filelist(Model model){
        return "manage/filedel";
    }
}

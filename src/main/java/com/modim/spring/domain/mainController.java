package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class mainController {

    private final BookService bookService;

    @GetMapping("/")
    public String root() {return "redirect:/bookList";}

    @GetMapping("/bookList")
    public String bookList(Model model, @RequestParam(value="page", defaultValue = "0")int page){
        Page<Book> bookList = this.bookService.bookList(page);
        model.addAttribute("bookList", bookList);
        return "book/book";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "member/login";
    }

    @GetMapping("/signup")
    public String signup(Model model){
        return "member/signup";

    }
}

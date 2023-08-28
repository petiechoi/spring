package com.modim.spring.domain;

import com.modim.spring.domain.book.model.Book;
import com.modim.spring.domain.book.service.BookService;
import com.modim.spring.domain.member.dto.MemberDto;
import com.modim.spring.domain.member.service.MemberService;
import com.modim.spring.domain.file.service.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class mainController {

    private final BookService bookService;
    private final MemberService memberService;

    private final S3FileService s3FileService;

    @Value("${cookie.name}")
    private String coockieName;

    @GetMapping("/")
    public String root() {return "redirect:/book/list";}

    @GetMapping("/book/list")
    public String bookList(Model model, @RequestParam(value="page", defaultValue = "0")int page){
        Page<Book> bookList = bookService.bookList(page);
        model.addAttribute("bookList", bookList);
        return "pages/bookList";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "pages/login";
    }

    @GetMapping("/join")
    public String join(Model model, @CookieValue(name="modim", required = false)String modim){
        if(modim == null)
            return "pages/join";
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(@Valid @RequestBody MemberDto.RequestDto requestDto){
        memberService.join(requestDto);
        return "redirect:/";
    }

    @GetMapping("/leave")
    public String leave(@CookieValue(name="modim", required = false)String coockieValue, HttpServletResponse response){
        if(coockieValue.length() > 0){
            Cookie cookie = new Cookie(coockieName, null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @GetMapping("/download")
    public String download(Model model){
        model.addAttribute("fileList",s3FileService.fileList());
        ////model.addAttribute("")
        return "pages/downloadList";
    }
}

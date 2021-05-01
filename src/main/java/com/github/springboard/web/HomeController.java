package com.github.springboard.web;

import com.github.springboard.security.CurrentMember;
import com.github.springboard.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home(@CurrentMember Member member, Model model) {
        model.addAttribute("member", member);
        return "home";
    }

}

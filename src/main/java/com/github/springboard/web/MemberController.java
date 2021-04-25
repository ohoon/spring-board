package com.github.springboard.web;

import com.github.springboard.domain.Member;
import com.github.springboard.dto.MemberSignUpForm;
import com.github.springboard.exception.DuplicateMemberException;
import com.github.springboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/signup")
    public String signUpForm(Model model) {
        model.addAttribute("signUpForm", new MemberSignUpForm());
        return "members/signUpForm";
    }

    @PostMapping("/members/signup")
    public String signUp(@Valid @ModelAttribute("signUpForm") MemberSignUpForm form, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "members/signUpForm";
            }

            memberService.join(Member.create(form.getUsername(), form.getPassword(), form.getNickname(), form.getEmail()));
            return "redirect:/";
        } catch (DuplicateMemberException e) {
            return "members/signUpForm";
        }
    }

}

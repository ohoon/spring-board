package com.github.springboard.web;

import com.github.springboard.domain.Member;
import com.github.springboard.domain.Post;
import com.github.springboard.domain.PostType;
import com.github.springboard.dto.PostListDto;
import com.github.springboard.dto.PostSearchCondition;
import com.github.springboard.dto.PostWriteForm;
import com.github.springboard.security.CurrentMember;
import com.github.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String list(
            @ModelAttribute("condition") PostSearchCondition condition,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        Page<PostListDto> postPage = postService.search(condition, pageable).map(PostListDto::new);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "posts/list";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/posts/write")
    public String writeForm(Model model) {
        model.addAttribute("writeForm", new PostWriteForm());
        return "posts/writeForm";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @PostMapping("posts/write")
    public String write(
            @CurrentMember Member member,
            @Valid @ModelAttribute("writeForm") PostWriteForm form,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "posts/writeForm";
        }

        PostType type = form.getIsNotice() ? PostType.NOTICE : PostType.GENERAL;
        postService.write(member.getId(), form.getSubject(), form.getContent(), type);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String read(@PathVariable("id") Long postId, Model model) {
        Post post = postService.read(postId);
        postService.visit(postId);
        model.addAttribute("post", post);
        return "posts/read";
    }

}

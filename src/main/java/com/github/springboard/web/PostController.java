package com.github.springboard.web;

import com.github.springboard.dto.PostListDto;
import com.github.springboard.dto.PostSearchCondition;
import com.github.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

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

}

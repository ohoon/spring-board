package com.github.springboard.web;

import com.github.springboard.domain.Comment;
import com.github.springboard.domain.Post;
import com.github.springboard.security.CurrentMember;
import com.github.springboard.domain.Member;
import com.github.springboard.service.CommentService;
import com.github.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    private final CommentService commentService;

    @GetMapping("/")
    public String home(@CurrentMember Member member, Model model) {
        List<Post> recentPosts = postService.recentList();
        List<Comment> recentComments = commentService.recentList();
        model.addAttribute("member", member);
        model.addAttribute("recentPosts", recentPosts);
        model.addAttribute("recentComments", recentComments);
        return "home";
    }

}

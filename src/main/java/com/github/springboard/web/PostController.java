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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String list(
            @CurrentMember Member member,
            @ModelAttribute("condition") PostSearchCondition condition,
            @PageableDefault Pageable pageable,
            Model model
    ) {
        Page<Post> posts = postService.search(condition, pageable);
        Page<PostListDto> postPage = posts.map(PostListDto::new);
        model.addAttribute("member", member);
        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("totalPages", postPage.getTotalPages());
        return "posts/list";
    }

    @PreAuthorize("hasRole('MEMBER')")
    @GetMapping("/posts/write")
    public String writeForm(@CurrentMember Member member, Model model) {
        model.addAttribute("member", member);
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
    public String read(
            @CurrentMember Member member,
            @PathVariable("id") Long postId,
            Model model
    ) {
        Post post = postService.read(postId);
        postService.visit(postId);
        model.addAttribute("member", member);
        model.addAttribute("currentMemberId", member != null ? member.getId() : null);
        model.addAttribute("currentMemberUsername", member != null ? member.getUsername() : null);
        model.addAttribute("post", post);
        return "posts/read";
    }

    @PreAuthorize("@postService.isWriter(#postId, authentication.name)")
    @GetMapping("/posts/{id}/edit")
    public String editForm(
            @CurrentMember Member member,
            @PathVariable("id") Long postId,
            Model model
    ) {
        Post post = postService.read(postId);
        model.addAttribute("member", member);
        model.addAttribute("editForm", new PostWriteForm(post));
        return "posts/editForm";
    }

    @PreAuthorize("@postService.isWriter(#postId, authentication.name)")
    @PostMapping("/posts/{id}/edit")
    public String edit(
            @PathVariable("id") Long postId,
            @Valid @ModelAttribute("editForm") PostWriteForm form,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "posts/editForm";
        }

        postService.edit(
                postId,
                form.getSubject(),
                form.getContent(),
                form.getIsNotice() ? PostType.NOTICE : PostType.GENERAL
        );

        redirectAttributes.addAttribute("id", postId);
        return "redirect:/posts/{id}";
    }

    @PreAuthorize("@postService.isWriter(#postId, authentication.name)")
    @PostMapping("/posts/{id}/remove")
    public String remove(@PathVariable("id") Long postId) {
        postService.remove(postId);
        return "redirect:/posts";
    }


}

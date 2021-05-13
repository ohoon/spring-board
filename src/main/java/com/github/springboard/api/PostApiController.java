package com.github.springboard.api;

import com.github.springboard.domain.Member;
import com.github.springboard.dto.*;
import com.github.springboard.exception.*;
import com.github.springboard.security.CurrentMember;
import com.github.springboard.service.CommentService;
import com.github.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    private final CommentService commentService;

    @PostMapping("/api/posts/vote")
    public Result<Integer> vote(@Valid @RequestBody PostVoteDto postVoteDto) {
        Result<Integer> result = new Result<>();

        try {
            int voted = postService.vote(postVoteDto.getMemberId(), postVoteDto.getPostId(), postVoteDto.getIsLike());
            result.setSuccess(true);
            result.setData(voted);
            result.setMessage("정상적으로 " + (postVoteDto.getIsLike() ? "추천" : "비추천") + "되었습니다.");
        } catch (DuplicateVoteException | NotFoundMemberException | NotFoundPostException e) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @GetMapping("/api/posts/{id}/comments")
    public Result<Page<PostCommentDto>> readComments(@PathVariable("id") Long postId, Pageable pageable) {
        Result<Page<PostCommentDto>> result = new Result<>();

        try {
            Page<PostCommentDto> commentPage = commentService.list(postId, pageable).map(PostCommentDto::new);
            result.setSuccess(true);
            result.setData(commentPage);
            result.setMessage("정상적으로 불러왔습니다.");
        } catch (NotFoundMemberException | NotFoundPostException e) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("/api/posts/{id}/comments")
    public Result<Long> writeComment(@PathVariable("id") Long postId, @Valid @RequestBody PostCommentWriteDto commentWriteDto) {
        Result<Long> result = new Result<>();

        try {
            Long commentId = commentService.write(commentWriteDto.getMemberId(), postId, commentWriteDto.getContent());
            result.setSuccess(true);
            result.setData(commentId);
            result.setMessage("정상적으로 등록되었습니다.");
        } catch (NotFoundMemberException | NotFoundPostException e) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage(e.getMessage());
        }

        return result;
    }

    @PostMapping("/api/posts/{id}/comments/{cid}")
    public Result<Long> removeComment(
            @CurrentMember Member member,
            @PathVariable("id") Long postId,
            @PathVariable("cid") Long commentId,
            @Valid @RequestBody MemberSimpleAuthDto simpleAuthDto
    ) {
        Result<Long> result = new Result<>();

        try {
            if (!simpleAuthDto.getMemberId().equals(member.getId())) {
                throw new AuthenticationException("댓글을 삭제할 권한이 없습니다.");
            }
            commentService.remove(commentId);
            result.setSuccess(true);
            result.setData(commentId);
            result.setMessage("정상적으로 삭제되었습니다.");
        } catch (AuthenticationException | NotFoundCommentException e) {
            result.setSuccess(false);
            result.setData(null);
            result.setMessage(e.getMessage());
        }

        return result;
    }

}

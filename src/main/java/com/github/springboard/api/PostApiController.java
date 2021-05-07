package com.github.springboard.api;

import com.github.springboard.dto.Result;
import com.github.springboard.dto.PostVoteDto;
import com.github.springboard.exception.DuplicateVoteException;
import com.github.springboard.exception.NotFoundMemberException;
import com.github.springboard.exception.NotFoundPostException;
import com.github.springboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

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
            result.setMessage(e.getMessage());
        }

        return result;
    }

}

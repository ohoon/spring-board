package com.github.springboard.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PostVoteDto {

    @NotNull(message = "회원 ID가 필요합니다.")
    private Long memberId;

    @NotNull(message = "게시물 ID가 필요합니다.")
    private Long postId;

    @NotNull(message = "추천/비추천 구분이 필요합니다.")
    private Boolean isLike;

}

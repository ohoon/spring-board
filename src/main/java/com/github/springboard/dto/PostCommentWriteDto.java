package com.github.springboard.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PostCommentWriteDto {

    @NotNull(message = "회원 ID가 필요합니다.")
    private Long memberId;

    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;

}

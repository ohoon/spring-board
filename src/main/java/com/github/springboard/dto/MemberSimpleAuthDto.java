package com.github.springboard.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MemberSimpleAuthDto {

    @NotNull(message = "회원 ID가 필요합니다.")
    private Long memberId;

}

package com.github.springboard.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PostWriteForm {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;

    private String content;

    private Boolean isNotice = false;

}

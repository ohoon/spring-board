package com.github.springboard.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class MemberLoginForm {

    @NotEmpty(message = "아이디를 입력해주세요.")
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password;

}

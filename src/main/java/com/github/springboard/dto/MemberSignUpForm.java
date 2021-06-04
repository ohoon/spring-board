package com.github.springboard.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class MemberSignUpForm {

    @NotEmpty(message = "아이디를 입력해주세요.")
    @Size(min = 5, max = 20)
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20)
    private String password;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 20)
    private String nickname;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일을 입력해주세요.")
    private String email;

}

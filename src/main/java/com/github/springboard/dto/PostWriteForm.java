package com.github.springboard.dto;

import com.github.springboard.domain.Post;
import com.github.springboard.domain.PostType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class PostWriteForm {

    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;

    private String content;

    private Boolean isNotice = false;

    //== 생성자 ==//
    public PostWriteForm(Post post) {
        this.subject = post.getSubject();
        this.content = post.getContent();
        this.isNotice = post.getType() == PostType.NOTICE;
    }
}

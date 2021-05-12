package com.github.springboard.dto;

import com.github.springboard.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCommentDto {

    private Long id;

    private String content;

    private String nickname;

    private LocalDateTime createdDate;

    //== 생성자 ==//
    public PostCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getMember().getNickname();
        this.createdDate = comment.getCreatedDate();
    }

}

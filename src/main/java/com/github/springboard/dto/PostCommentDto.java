package com.github.springboard.dto;

import com.github.springboard.domain.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostCommentDto {

    private Long id;

    private String content;

    private String username;

    private String nickname;

    private LocalDateTime createdDate;

    private boolean isRemoved;

    //== 생성자 ==//
    public PostCommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getMember().getUsername();
        this.nickname = comment.getMember().getNickname();
        this.createdDate = comment.getCreatedDate();
        this.isRemoved = comment.isRemoved();
    }

}

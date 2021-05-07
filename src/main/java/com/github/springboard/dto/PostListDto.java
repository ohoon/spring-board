package com.github.springboard.dto;

import com.github.springboard.domain.Post;
import com.github.springboard.domain.PostType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostListDto {

    private Long id;

    private PostType type;

    private String subject;

    private String nickname;

    private LocalDateTime createdDate;

    private int hit;

    private int like;

    //== 생성자 ==//
    public PostListDto(Post post) {
        this.id = post.getId();
        this.type = post.getType();
        this.subject = post.getSubject();
        this.nickname = post.getMember().getNickname();
        this.createdDate = post.getCreatedDate();
        this.hit = post.getHit();
        this.like = post.getLike();
    }

}
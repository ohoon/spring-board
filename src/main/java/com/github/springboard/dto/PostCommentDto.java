package com.github.springboard.dto;

import com.github.springboard.domain.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostCommentDto extends PostCommentChildDto {

    private final List<PostCommentChildDto> children = new ArrayList<>();

    //== 생성자 ==//
    public PostCommentDto(Comment comment) {
        super(comment);

        this.children.addAll(comment.getChildren().stream()
                .map(PostCommentChildDto::new).collect(Collectors.toList()));
    }

    //== Getter ==//
    public List<PostCommentChildDto> getChildren() {
        return children;
    }

}

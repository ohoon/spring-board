package com.github.springboard.dto;

import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class PostSearchCondition {

    @Enumerated(EnumType.STRING)
    private PostSearchMode mode = PostSearchMode.SUBJECT_CONTENT;

    private String keyword;

}

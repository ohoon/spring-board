package com.github.springboard.dto;

import lombok.Data;

@Data
public class PostSearchCondition {

    private String subjectOrContent;

    private String subject;

    private String content;

    private String username;

}

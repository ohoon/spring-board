package com.github.springboard.dto;

import lombok.Data;

@Data
public class Result<T> {

    private boolean isSuccess;

    private T data;

    private String message;

}

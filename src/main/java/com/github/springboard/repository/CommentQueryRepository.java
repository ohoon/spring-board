package com.github.springboard.repository;

import com.github.springboard.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentQueryRepository {

    Page<Comment> list(Long postId, Pageable pageable);

}

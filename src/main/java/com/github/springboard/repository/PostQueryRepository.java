package com.github.springboard.repository;

import com.github.springboard.domain.Post;
import com.github.springboard.dto.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryRepository {

    Page<Post> search(PostSearchCondition condition, Pageable pageable);

}

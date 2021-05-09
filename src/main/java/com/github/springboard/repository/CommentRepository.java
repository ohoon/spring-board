package com.github.springboard.repository;

import com.github.springboard.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentQueryRepository {

}

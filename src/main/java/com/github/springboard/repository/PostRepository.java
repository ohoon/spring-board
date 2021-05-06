package com.github.springboard.repository;

import com.github.springboard.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryRepository {

    @EntityGraph(attributePaths = {"member"})
    Optional<Post> findWithMemberById(Long id);

    @EntityGraph(attributePaths = {"votes"})
    Optional<Post> findWithVotesById(Long id);

}

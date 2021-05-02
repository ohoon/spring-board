package com.github.springboard.service;

import com.github.springboard.domain.Member;
import com.github.springboard.domain.Post;
import com.github.springboard.domain.PostType;
import com.github.springboard.dto.PostSearchCondition;
import com.github.springboard.exception.NotFoundMemberException;
import com.github.springboard.exception.NotFoundPostException;
import com.github.springboard.repository.MemberRepository;
import com.github.springboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public Long write(Long memberId, String subject, String content, PostType type) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

        Post post = Post.create(subject, content, type, member);
        Post newPost = postRepository.save(post);
        return newPost.getId();
    }

    public Post read(Long postId) {
        return postRepository.findWithMemberById(postId)
                .orElseThrow(() -> new NotFoundPostException("존재하지 않는 게시물입니다."));
    }

    public Page<Post> search(PostSearchCondition condition, Pageable pageable) {
        return postRepository.search(condition, pageable);
    }

    @Transactional
    public void edit(Long postId, String subject, String content, PostType type) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("존재하지 않는 게시물입니다."));

        post.edit(subject, content, type);
    }

    @Transactional
    public void visit(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("존재하지 않는 게시물입니다."));

        post.visit();
    }

    public boolean isWriter(Long postId, String username) {
        Post post = postRepository.findWithMemberById(postId)
                .orElseThrow(() -> new NotFoundPostException("존재하지 않는 게시물입니다."));

        return post.getMember().getUsername().equals(username);
    }

}

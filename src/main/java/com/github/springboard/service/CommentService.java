package com.github.springboard.service;

import com.github.springboard.domain.Comment;
import com.github.springboard.domain.Member;
import com.github.springboard.domain.Post;
import com.github.springboard.exception.NotFoundCommentException;
import com.github.springboard.exception.NotFoundMemberException;
import com.github.springboard.exception.NotFoundPostException;
import com.github.springboard.repository.CommentRepository;
import com.github.springboard.repository.MemberRepository;
import com.github.springboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final PostRepository postRepository;

    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Page<Comment> list(Long postId, Pageable pageable) {
        return commentRepository.list(postId, pageable);
    }

    public List<Comment> recentList() {
        return commentRepository.findFirst6ByOrderByIdDesc();
    }

    public int countByPostId(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Transactional
    public Long write(Long memberId, Long postId, String content, Long parentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("존재하지 않는 게시물입니다."));

        Comment comment = Comment.create(content, post, member);

        if (parentId != null) {
            Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new NotFoundCommentException("존재하지 않는 댓글입니다."));

            comment.assignParent(parent);
        }

        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public void edit(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException("존재하지 않는 댓글입니다."));

        comment.changeContent(content);
    }

    @Transactional
    public void remove(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException("존재하지 않는 댓글입니다."));

        comment.remove();
    }

}

package com.github.springboard.service;

import com.github.springboard.domain.Comment;
import com.github.springboard.domain.PostType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    MemberService memberService;

    @Autowired
    PostService postService;

    @Autowired
    EntityManager entityManager;

    @Test
    void write() {
        Long memberId = memberService.join("ohoon", "password", "ohoon", "ohoon@example.org");

        Long postId = postService.write(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        commentService.write(memberId, postId, "test comment!", null);
        Page<Comment> result = commentService.list(postId, PageRequest.of(0, 10));

        assertThat(result)
                .extracting("content")
                .containsExactly("test comment!");
    }

    @Test
    void edit() {
        Long memberId = memberService.join("ohoon", "password", "ohoon", "ohoon@example.org");

        Long postId = postService.write(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        Long commentId = commentService.write(memberId, postId, "test comment!", null);
        Comment comment = commentService.findById(commentId).get();

        comment.changeContent("change!");
        entityManager.flush();
        entityManager.clear();

        Comment changedComment = commentService.findById(commentId).get();

        assertThat(changedComment.getContent()).isEqualTo("change!");
    }

    @Test
    void remove() {
        Long memberId = memberService.join("ohoon", "password", "ohoon", "ohoon@example.org");

        Long postId = postService.write(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        Long commentId = commentService.write(memberId, postId, "test comment!", null);

        commentService.remove(commentId);
        Comment comment = commentService.findById(commentId).get();

        assertThat(comment.isRemoved()).isTrue();
    }

    @Test
    void recentList() {
        Long memberId = memberService.join("ohoon", "password", "ohoon", "ohoon@example.org");

        Long postId = postService.write(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        for (int i = 0; i < 10; i++) {
            commentService.write(memberId, postId, "test comment" + i, null);
        }

        List<Comment> comments = commentService.recentList();

        assertThat(comments.size()).isEqualTo(6);
        assertThat(comments)
                .extracting("content")
                .containsExactly("test comment9", "test comment8", "test comment7", "test comment6", "test comment5");
    }

}
package com.github.springboard.service;

import com.github.springboard.domain.Member;
import com.github.springboard.domain.Post;
import com.github.springboard.domain.PostType;
import com.github.springboard.dto.PostSearchCondition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    @Test
    void findOne() {
        //given
        Member member = Member.create("ohoon", "password", "ohoon", "ohoon@example.org");
        Long memberId = memberService.join(member);

        Long postId = postService.post(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        //when
        Post findPost = postService.findOne(postId);

        //then
        assertThat(findPost.getSubject()).isEqualTo("testing");
        assertThat(findPost.getMember()).isEqualTo(member);
    }

    @Test
    void findAll() {
        //given
        Member member = Member.create("ohoon", "password", "ohoon", "ohoon@example.org");
        Long memberId = memberService.join(member);

        Long postId = postService.post(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        Long postId2 = postService.post(
                memberId,
                "testing2",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        Long postId3 = postService.post(
                memberId,
                "testing3",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.NOTICE
        );

        //when
        Page<Post> result = postService.findAll(new PostSearchCondition(), PageRequest.of(0, 10));

        //then
        assertThat(result)
                .extracting("subject")
                .containsExactly("testing", "testing2", "testing3");
    }

    @Test
    void edit() {
        //given
        Member member = Member.create("ohoon", "password", "ohoon", "ohoon@example.org");
        Long memberId = memberService.join(member);

        Long postId = postService.post(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        //when
        postService.edit(
                postId,
                "modifying!",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        Post findPost = postService.findOne(postId);

        //then
        assertThat(findPost.getSubject()).isEqualTo("modifying!");
    }

    @Test
    void visit() {
        //given
        Member member = Member.create("ohoon", "password", "ohoon", "ohoon@example.org");
        Long memberId = memberService.join(member);

        Long postId = postService.post(
                memberId,
                "testing",
                "Sometimes, you need to mark parts of your code for future reference:" +
                        " areas of optimization and improvement, possible changes, questions to be discussed, and so on." +
                        " IntelliJ IDEA lets you add special types of comments that are highlighted in the editor, indexed, and" +
                        " listed in the TODO tool window." +
                        " This way you and your teammates can keep track of issues that require attention.\n",
                PostType.GENERAL
        );

        //when
        postService.visit(postId);
        postService.visit(postId);
        postService.visit(postId);
        Post findPost = postService.findOne(postId);

        //then
        assertThat(findPost.getHit()).isEqualTo(3);
    }

}
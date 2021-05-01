package com.github.springboard.service;

import com.github.springboard.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @BeforeEach
    void init() {
        memberService.join("memberA", "asd123aaaaa", "hong", "asd@a.a");
        memberService.join("memberB", "123aaaaaaaa", "kang", "asdfd@asd.fd");
        memberService.join("memberC", "aasdddsd23", "kim", "asdgasd@google.com");
        memberService.join("memberD", "asd11111asd", "kwon", "asd@example.org");
        memberService.join("memberE", "sdsdasdasdasd", "park", "a@naver.com");
    }

    @Test
    void findOne() {
        Long memberId = memberService.join("testMember", "asd2333", "test", "test@naver.com");
        Member findMember = memberService.findOne(memberId);

        assertThat(findMember.getId()).isEqualTo(memberId);
    }

    @Test
    void findByUsername() {
        List<Member> findMembers = memberService.findByUsername("memberA");

        assertThat(findMembers.size()).isEqualTo(1);
        assertThat(findMembers.get(0).getNickname()).isEqualTo("hong");
    }

    @Test
    void findAll() {
        List<Member> members = memberService.findAll();

        assertThat(members.size()).isEqualTo(5);
        assertThat(members)
                .extracting("username")
                .containsExactly("memberA", "memberB", "memberC", "memberD", "memberE");
    }

    @Test
    void updateOne() {
        memberService.updateOne(1L, "", "choi", "asd@asd.com");
        em.flush();
        em.clear();
        Member findMember = memberService.findOne(1L);

        assertThat(findMember.getNickname()).isEqualTo("choi");
        assertThat(findMember.getPassword()).isEqualTo("asd123aaaaa");
    }

}
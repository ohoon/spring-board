package com.github.springboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int hit;

    @Enumerated(EnumType.STRING)
    private PostType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 생성자 ==//
    private Post(String subject, String content, Member member) {
        this.subject = subject;
        this.content = content;
        this.type = PostType.GENERAL;
        this.member = member;
    }

    private Post(String subject, String content, PostType type, Member member) {
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.member = member;
    }

    //== 생성 메서드==//
    public static Post create(String subject, String content, Member member) {
        return new Post(subject, content, member);
    }

    public static Post create(String subject, String content, PostType type, Member member) {
        return new Post(subject, content, type, member);
    }

    //== 비즈니스 로직==//
    public void edit(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    public void edit(String subject, String content, PostType type) {
        this.subject = subject;
        this.content = content;
        this.type = type;
    }

    public void visit() {
        this.hit++;
    }

}

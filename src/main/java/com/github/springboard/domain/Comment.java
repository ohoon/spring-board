package com.github.springboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isRemoved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private final List<Comment> children = new ArrayList<>();

    //== 생성자 ==//
    private Comment(String content, Post post, Member member) {
        this.content = content;
        this.post = post;
        this.member = member;
    }

    //== 생성 메서드 ==//
    public static Comment create(String content, Post post, Member member) {
        return new Comment(content, post, member);
    }

    //== 비즈니스 로직 ==//
    public void changeContent(String content) {
        this.content = content;
    }

    public void remove() {
        this.isRemoved = true;
    }

    public void assignParent(Comment parent) {
        this.parent = parent;
        parent.getChildren().add(this);
    }

}

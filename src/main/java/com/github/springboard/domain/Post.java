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
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "post")
    private final List<Comment> comments = new ArrayList<>();

    private int hit;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Vote> votes = new ArrayList<>();

    @Column(name = "likes")
    private int like;

    @Column(name = "hates")
    private int hate;

    @Enumerated(EnumType.STRING)
    private PostType type;

    private boolean isRemoved;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 생성자 ==//
    private Post(String subject, String content, PostType type, Member member) {
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.member = member;
    }

    //== 생성 메서드==//
    public static Post create(String subject, String content, PostType type, Member member) {
        return new Post(subject, content, type, member);
    }

    //== 비즈니스 로직==//
    public void edit(String subject, String content, PostType type) {
        this.subject = subject;
        this.content = content;
        this.type = type;
    }

    public void remove() {
        this.isRemoved = true;
    }

    public void visit() {
        this.hit++;
    }

    public void vote(Vote vote) {
        this.getVotes().add(vote);
        vote.assignPost(this);

        if (vote.isLike()) {
            this.like++;
        } else {
            this.hate++;
        }
    }

}

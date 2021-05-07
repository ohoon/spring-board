package com.github.springboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

    @Id
    @GeneratedValue
    @Column(name = "vote_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private boolean isLike;

    //== 생성자 ==//
    private Vote(Member member, boolean isLike) {
        this.member = member;
        this.isLike = isLike;
    }

    //== 생성 메서드 ==//
    public static Vote create(Member member, boolean isLike) {
        return new Vote(member, isLike);
    }

    //== 비즈니스 로직 ==//
    public void assignPost(Post post) {
        this.post = post;
    }

}

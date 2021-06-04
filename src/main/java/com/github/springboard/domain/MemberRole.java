package com.github.springboard.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRole {

    @Id
    @GeneratedValue
    @Column(name = "member_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    //== 생성자 ==//
    private MemberRole(Role role) {
        this.role = role;
    }

    //== 생성 메서드 ==//
    public static MemberRole create(Role role) {
        return new MemberRole(role);
    }

    //== 비즈니스 로직 ==//
    public void assignMember(Member member) {
        this.member = member;
    }

}

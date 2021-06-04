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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @OneToMany(
            mappedBy = "member",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<MemberRole> memberRoles = new ArrayList<>();

    //== 생성자 ==//
    private Member(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    //== 생성 메서드 ==//
    public static Member create(String username, String password, String nickname, String email, MemberRole... memberRoles) {
        Member member = new Member(username, password, nickname, email);
        for (MemberRole memberRole : memberRoles) {
            member.addMemberRole(memberRole);
        }

        return member;
    }

    //== 비즈니스 로직 ==//
    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void addMemberRole(MemberRole memberRole) {
        this.getMemberRoles().add(memberRole);
        memberRole.assignMember(this);
    }

}

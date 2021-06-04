package com.github.springboard.security;

import com.github.springboard.domain.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

@Getter
public class MemberDetails extends User {

    private final Member member;

    public MemberDetails(Member member) {
        super(
                member.getUsername(),
                member.getPassword(),
                member.getMemberRoles().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().getName()))
                        .collect(Collectors.toList())
        );

        this.member = member;
    }

}

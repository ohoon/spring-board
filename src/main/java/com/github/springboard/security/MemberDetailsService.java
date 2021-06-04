package com.github.springboard.security;

import com.github.springboard.domain.Member;
import com.github.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Member> members = memberRepository.findByUsername(username);
        if (members.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        Member member = members.get(0);
        return new MemberDetails(member);
    }

}

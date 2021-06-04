package com.github.springboard.service;

import com.github.springboard.domain.Member;
import com.github.springboard.exception.DuplicateMemberException;
import com.github.springboard.exception.NotFoundMemberException;
import com.github.springboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(String username, String password, String nickname, String email) {
        validateDuplicateUsername(username);
        String encodePassword = passwordEncoder.encode(password);
        Member member = Member.create(username, encodePassword, nickname, email);
        Member newMember = memberRepository.save(member);
        return newMember.getId();
    }

    public Member findOne(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        return findMember.orElse(null);
    }

    public List<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional
    public void updateOne(Long memberId, String password, String nickname, String email) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

        if (StringUtils.hasText(password)) {
            findMember.changePassword(password);
        }

        findMember.changeNickname(nickname);
        findMember.changeEmail(email);
    }

    private void validateDuplicateUsername(String username) {
        List<Member> members = memberRepository.findByUsername(username);
        if (!members.isEmpty()) {
            throw new DuplicateMemberException("이미 존재하는 아이디입니다.");
        }
    }

}

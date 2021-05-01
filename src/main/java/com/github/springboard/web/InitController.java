package com.github.springboard.web;

import com.github.springboard.domain.Member;
import com.github.springboard.domain.MemberRole;
import com.github.springboard.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        private final PasswordEncoder passwordEncoder;

        @Transactional
        public void init() {
            Role roleA = Role.create("MEMBER");
            Role roleB = Role.create("ADMIN");
            em.persist(roleA);
            em.persist(roleB);

            Member memberA = Member.create(
                    "dudgns5661",
                    passwordEncoder.encode("young6354"),
                    "영훈님",
                    "0hoon5661@gmail.com",
                    MemberRole.create(roleA)
            );
            Member memberB = Member.create(
                    "0hoon5661",
                    passwordEncoder.encode("young6354"),
                    "Young.K",
                    "dudgns5661@naver.com",
                    MemberRole.create(roleA),
                    MemberRole.create(roleB)
            );
            Member memberC = Member.create(
                    "ohoon",
                    passwordEncoder.encode("young6354"),
                    "강영",
                    "dudgns56611@naver.com",
                    MemberRole.create(roleA)
            );
            em.persist(memberA);
            em.persist(memberB);
            em.persist(memberC);
        }

    }
}

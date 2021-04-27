package com.github.springboard.web;

import com.github.springboard.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
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

        @Transactional
        public void init() {
            Member memberA = Member.create("dudgns5661", "young6354", "영훈님", "0hoon5661@gmail.com");
            Member memberB = Member.create("0hoon5661", "young6354", "Young.K", "dudgns5661@naver.com");
            Member memberC = Member.create("ohoon", "young6354", "강영", "dudgns56611@naver.com");
            em.persist(memberA);
            em.persist(memberB);
            em.persist(memberC);
        }

    }
}

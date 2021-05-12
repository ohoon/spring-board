package com.github.springboard.repository;

import com.github.springboard.domain.Post;
import com.github.springboard.domain.QComment;
import com.github.springboard.dto.PostSearchCondition;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.github.springboard.domain.QComment.comment;
import static com.github.springboard.domain.QMember.member;
import static com.github.springboard.domain.QPost.post;
import static com.github.springboard.util.NullSafeBuilder.nullSafeBuilder;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class PostQueryRepositoryImpl implements PostQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> search(PostSearchCondition condition, Pageable pageable) {
        List<Post> content = queryFactory
                .selectFrom(post)
                .leftJoin(post.member, member).fetchJoin()
                .leftJoin(post.comments, comment).fetchJoin()
                .where(allContains(condition).and(post.isRemoved.isFalse()))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory
                .selectFrom(post)
                .where(allContains(condition).and(post.isRemoved.isFalse()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanBuilder allContains(PostSearchCondition condition) {
        return subjectOrContentContains(condition.getSubjectOrContent())
                .and(subjectContains(condition.getSubject()))
                .and(contentContains(condition.getContent()))
                .and(nicknameContains(condition.getUsername()));
    }

    private BooleanBuilder subjectOrContentContains(String subjectOrContent) {
        return subjectContains(subjectOrContent)
                .or(contentContains(subjectOrContent));
    }

    private BooleanBuilder subjectContains(String subject) {
        return nullSafeBuilder(() -> post.subject.contains(hasText(subject) ? subject : null));
    }

    private BooleanBuilder contentContains(String content) {
        return nullSafeBuilder(() -> post.content.contains(hasText(content) ? content : null));
    }

    private BooleanBuilder nicknameContains(String nickname) {
        return nullSafeBuilder(() -> post.member.nickname.contains(hasText(nickname) ? nickname : null));
    }

}

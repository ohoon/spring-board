package com.github.springboard.repository;

import com.github.springboard.domain.Post;
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
                .where(searchFilter(condition).and(post.isRemoved.isFalse()))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Post> countQuery = queryFactory
                .selectFrom(post)
                .where(searchFilter(condition).and(post.isRemoved.isFalse()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanBuilder searchFilter(PostSearchCondition condition) {
        String keyword = condition.getKeyword();

        switch (condition.getMode()) {
            case SUBJECT_CONTENT:
                return subjectOrContentContains(keyword);
            case SUBJECT:
                return subjectContains(keyword);
            case CONTENT:
                return contentContains(keyword);
            case WRITER:
                return writerContains(keyword);
            default:
                return new BooleanBuilder();
        }
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

    private BooleanBuilder writerContains(String writer) {
        return usernameContains(writer)
                .or(nicknameContains(writer));
    }

    private BooleanBuilder usernameContains(String username) {
        return nullSafeBuilder(() -> post.member.username.contains(hasText(username) ? username : null));
    }

    private BooleanBuilder nicknameContains(String nickname) {
        return nullSafeBuilder(() -> post.member.nickname.contains(hasText(nickname) ? nickname : null));
    }

}

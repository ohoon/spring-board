package com.github.springboard.repository;

import com.github.springboard.domain.Comment;
import com.github.springboard.domain.QComment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.github.springboard.domain.QComment.comment;
import static com.github.springboard.domain.QMember.member;

@RequiredArgsConstructor
public class CommentQueryRepositoryImpl implements CommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Comment> list(Long postId, Pageable pageable) {
        List<Comment> content = queryFactory
                .selectFrom(comment)
                .leftJoin(comment.member, member).fetchJoin()
                .leftJoin(comment.children, new QComment("another")).fetchJoin()
                .where(comment.post.id.eq(postId).and(comment.parent.isNull()))
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Comment> countQuery = queryFactory
                .selectFrom(comment)
                .where(comment.post.id.eq(postId).and(comment.parent.isNull()));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

}

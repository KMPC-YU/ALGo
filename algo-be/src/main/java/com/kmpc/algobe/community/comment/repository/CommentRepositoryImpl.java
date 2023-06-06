package com.kmpc.algobe.community.comment.repository;

import com.kmpc.algobe.community.comment.domain.dto.CommentListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kmpc.algobe.community.comment.domain.entity.QComment.comment;
import static com.kmpc.algobe.community.post.domain.entity.QPost.post;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommentListDto> findAllByPostId(Long postId, Pageable pageable) {
        final Coalesce<Long> coalesce = new Coalesce<Long>().add(comment.parent).add(comment.commentId);
        List<CommentListDto> results = queryFactory
                .select(Projections.constructor(CommentListDto.class,
                        comment.commentId,
                        comment.content,
                        comment.nickname,
                        comment.parent,
                        comment.isDeleted,
                        comment.createdAt,
                        comment.modifiedAt
                        ))
                .from(comment)
                .where(comment.post.postId.eq(postId))
                .innerJoin(comment.post, post)
                .orderBy(coalesce.asc(), comment.commentId.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalSize = queryFactory
                .select(Wildcard.count)
                .from(comment)
                .where(comment.post.postId.eq(postId))
                .innerJoin(comment.post, post)
                .fetchOne();

        if (totalSize == null) {
            return null; // TODO Throw
        }

        return new PageImpl<>(results, pageable, totalSize);
    }


}

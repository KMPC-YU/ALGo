package com.kmpc.algobe.community.post.repository;

import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.kmpc.algobe.community.post.domain.dto.PostListDto;
import com.kmpc.algobe.community.post.domain.dto.PostSearchType;
import com.kmpc.algobe.community.post.domain.dto.QuestionListDto;
import com.kmpc.algobe.community.post.domain.entity.*;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.kmpc.algobe.community.board.domain.entity.QBoard.board;
import static com.kmpc.algobe.community.post.domain.entity.QAnonymous.anonymous;
import static com.kmpc.algobe.community.post.domain.entity.QPost.post;
import static com.kmpc.algobe.community.post.domain.entity.QQuestion.question;
import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Post.class, "post");
            orders.add(new OrderSpecifier(direction, orderByExpression.get(prop)));
            orders.add(new OrderSpecifier(Order.DESC, post.createdAt));
        });
        return orders;
    }

    @Override
    public Page<PostListDto> findAllByBoardBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType) {
        List<PostListDto> results = queryFactory
                .select(Projections.constructor(PostListDto.class,
                        post.postId,
                        post.title,
                        new CaseBuilder()
                                .when(post.board.boardType.eq(BoardType.ANONYMOUS)).then("익명").otherwise(post.user.nickname).as("author"),
                        post.viewCount,
                        post.likeCount,
                        post.commentCount,
                        post.isNotice,
                        post.createdAt
                ))
                .from(post)
                .innerJoin(post.board, board)
                .where(post.board.boardId.eq(boardId), post.isNotice.eq(false), isSearchable(keyword, searchType))
                .orderBy(getOrderSpecifier(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalSize = queryFactory
                .select(Wildcard.count)
                .from(post)
                .innerJoin(post.board, board)
                .where(post.board.boardId.eq(boardId), post.isNotice.eq(false), isSearchable(keyword, searchType))
                .fetchOne();

        if (totalSize == null) {
            return null; // TODO Throw
        }

        return new PageImpl<>(results, pageable, totalSize);
    }

    @Override
    public Page<QuestionListDto> findAllQuestionByBoardBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType) {
        List<QuestionListDto> results = queryFactory
                .select(Projections.constructor(QuestionListDto.class,
                        question.postId,
                        question.title,
                        question.user.nickname,
                        question.point,
                        question.viewCount,
                        question.likeCount,
                        question.commentCount,
                        question.isNotice,
                        question._super.createdAt
                ))
                .from(question)
                .innerJoin(question.board, board)
                .where(question.board.boardId.eq(boardId), question.isNotice.eq(false), isSearchable(keyword, searchType))
                .orderBy(getOrderSpecifier(pageable.getSort()).toArray(OrderSpecifier[]::new))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalSize = queryFactory
                .select(Wildcard.count)
                .from(question)
                .innerJoin(question.board, board)
                .where(question.board.boardId.eq(boardId), question.isNotice.eq(false), isSearchable(keyword, searchType))
                .fetchOne();

        if (totalSize == null) {
            return null; // TODO Throw
        }

        return new PageImpl<>(results, pageable, totalSize);
    }

    @Override
    public List<PostListDto> findAllNoticeByBoardId(Long boardId) {
        return queryFactory
                .select(Projections.constructor(PostListDto.class,
                        post.postId,
                        post.title,
                        post.user.nickname,
                        post.viewCount,
                        post.likeCount,
                        post.commentCount,
                        post.isNotice,
                        post.createdAt
                ))
                .from(post)
                .innerJoin(post.board, board)
                .where(post.board.boardId.eq(boardId), post.isNotice.eq(true))
                .orderBy(post.createdAt.desc())
                .fetch();
    }

    @Override
    public Optional<Question> findByQuestionId(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(question)
                .from(question)
                .where(question.postId.eq(postId))
                .fetchOne());
    }

    @Override
    public Optional<Post> findByPostId(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(post)
                .from(post)
                .where(post.postId.eq(postId))
                .fetchOne());
    }

    @Override
    public Optional<Anonymous> findByAnonymousId(Long postId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(anonymous)
                .where(anonymous.postId.eq(postId))
                .fetchOne());
    }

    BooleanExpression isSearchable(String keyword, PostSearchType searchType) {
        if (!hasText(keyword) || searchType == null)
            return null;

        if (searchType == PostSearchType.TITLE)
            return post.title.contains(keyword);
        else if (searchType == PostSearchType.CONTENT)
            return post.content.contains(keyword); // TODO Elastic Search 사용
        else if (searchType == PostSearchType.AUTHOR)
            return post.user.nickname.eq(keyword);
        else
            return post.title.contains(keyword).or(post.content.contains(keyword));
    }
}

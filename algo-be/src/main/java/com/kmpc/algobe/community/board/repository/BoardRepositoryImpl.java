package com.kmpc.algobe.community.board.repository;

import com.kmpc.algobe.community.board.domain.dto.BoardListDto;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kmpc.algobe.community.board.domain.entity.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardListDto> findAllByBoardType(BoardType boardType) {
        List<BoardListDto> results = queryFactory
                .select(Projections.constructor(BoardListDto.class,
                        board.boardId,
                        board.boardName,
                        board.boardType))
                .from(board)
                .where(board.boardType.eq(boardType))
                .orderBy(board.boardId.asc())
                .fetch();

        return results;
    }
}

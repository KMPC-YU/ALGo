package com.kmpc.algobe.community.board.repository;

import com.kmpc.algobe.community.board.domain.dto.BoardListDto;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {
    List<BoardListDto> findAllByBoardType(BoardType boardType);
}

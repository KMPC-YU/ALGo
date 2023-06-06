package com.kmpc.algobe.community.board.repository;

import com.kmpc.algobe.community.board.domain.dto.BoardListDto;
import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
    Boolean existsBoardByBoardName(String boardName);
}

package com.kmpc.algobe.community.board.service;

import com.kmpc.algobe.community.board.domain.dto.BoardCreateRequestDto;
import com.kmpc.algobe.community.board.domain.dto.BoardListDto;
import com.kmpc.algobe.community.board.domain.dto.BoardResponseDto;
import com.kmpc.algobe.community.board.domain.dto.BoardUpdateRequestDto;
import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.kmpc.algobe.community.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<BoardListDto> findAllBoards(BoardType category) {
        if(category == null){
            throw new RuntimeException();
        }
        return boardRepository.findAllByBoardType(category);
    }

    @Transactional
    public Long createBoard(BoardCreateRequestDto requestDto) {
        String boardName = requestDto.getBoardName();
        if(boardRepository.existsBoardByBoardName(boardName)){
            throw new RuntimeException("이미 존재하는 게시판입니다."); // TODO 예외처리
        }
        return boardRepository.save(Board.builder().boardName(boardName).boardType(requestDto.getBoardType()).build()).getBoardId();
    }

    @Transactional
    public Long updateBoard(BoardUpdateRequestDto requestDto, Long boardId){
        Board board = boardRepository.findById(boardId).orElseThrow();
        return boardRepository.save(board.updateBoard(requestDto.getBoardName())).getBoardId();
    }

    @Transactional
    public Long deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        boardRepository.delete(board);
        return board.getBoardId();
    }

    @Transactional(readOnly = true)
    public BoardResponseDto findBoardById(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        return new BoardResponseDto(board.getBoardName(), board.getBoardType().toString());
    }
}

package com.kmpc.algobe.community.board.controller;

import com.kmpc.algobe.community.board.domain.dto.BoardCreateRequestDto;
import com.kmpc.algobe.community.board.domain.dto.BoardListDto;
import com.kmpc.algobe.community.board.domain.dto.BoardResponseDto;
import com.kmpc.algobe.community.board.domain.dto.BoardUpdateRequestDto;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.kmpc.algobe.community.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardListDto>> getBoardListByCategory(@RequestParam(value = "category") BoardType category){
        List<BoardListDto> boardListDto = boardService.findAllBoards(category);
        return ResponseEntity.status(HttpStatus.OK).body(boardListDto);
    }

    @GetMapping("/{board_id}/types")
    public ResponseEntity<BoardResponseDto> getBoard(@PathVariable("board_id") Long boardId){
        BoardResponseDto boardResponseDto = boardService.findBoardById(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(boardResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Long> createBoard(@RequestBody @Valid BoardCreateRequestDto requestDto){
        Long createBoardId = boardService.createBoard(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(createBoardId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{board_id}")
    public ResponseEntity<Long> updateBoard(@RequestBody @Valid BoardUpdateRequestDto requestDto, @PathVariable("board_id") Long boardId){
        Long updateBoardId = boardService.updateBoard(requestDto, boardId);
        return ResponseEntity.status(HttpStatus.OK).body(updateBoardId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{board_id}")
    public ResponseEntity<Long> deleteBoard(@PathVariable("board_id") Long boardId){
        Long deleteBoardId = boardService.deleteBoard(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(deleteBoardId);
    }
}

package com.kmpc.algobe.community.comment.controller;

import com.kmpc.algobe.annotation.CurrentUser;
import com.kmpc.algobe.community.comment.domain.dto.CommentCreateRequestDto;
import com.kmpc.algobe.community.comment.domain.dto.CommentListDto;
import com.kmpc.algobe.community.comment.domain.dto.CommentListResponseDto;
import com.kmpc.algobe.community.comment.domain.dto.CommentUpdateRequestDto;
import com.kmpc.algobe.community.comment.service.CommentService;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{board_id}/posts/{post_id}/comments")
    public ResponseEntity<CommentListResponseDto> getCommentList(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable){
        CommentListResponseDto responseDto = commentService.findAllCommentsByPostId(boardId, postId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/{board_id}/posts/{post_id}/comments")
    public ResponseEntity<Long> createComment(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @RequestBody @Valid CommentCreateRequestDto requestDto, @CurrentUser User user){
        Long createCommentId = commentService.createComment(boardId, postId, requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createCommentId);
    }

    @PatchMapping("/{board_id}/posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<Long> updateComment(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId, @RequestBody @Valid CommentUpdateRequestDto requestDto, @CurrentUser User user){
        Long updateCommentId = commentService.updateComment(boardId, postId, commentId, requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(updateCommentId);
    }

    @DeleteMapping("/{board_id}/posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<Long> deleteComment(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId, @CurrentUser User user){
        Long deleteCommentId = commentService.deleteComment(boardId, postId, commentId, user);
        return ResponseEntity.status(HttpStatus.OK).body(deleteCommentId);
    }
}

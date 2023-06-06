package com.kmpc.algobe.community.post.controller;

import com.kmpc.algobe.annotation.CurrentUser;
import com.kmpc.algobe.community.post.service.PostLikeService;
import com.kmpc.algobe.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class PostLikeController {
    private final PostLikeService postLikeService;

    @PostMapping("/{board_id}/posts/{post_id}/likes")
    public ResponseEntity<Long> createPostLike(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @CurrentUser User user) {
        Long resultPostLikeId = postLikeService.createPostLike(boardId, postId, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultPostLikeId);
    }


    @DeleteMapping("/{board_id}/posts/{post_id}/likes")
    public ResponseEntity<Long> deletePostLike(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @CurrentUser User user){
        Long resultPostLikeId = postLikeService.deletePostLike(boardId, postId, user);
        return ResponseEntity.status(HttpStatus.OK).body(resultPostLikeId);
    }

}

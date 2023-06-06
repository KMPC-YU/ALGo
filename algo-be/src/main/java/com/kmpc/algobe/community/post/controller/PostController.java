package com.kmpc.algobe.community.post.controller;

import com.kmpc.algobe.annotation.CurrentUser;
import com.kmpc.algobe.community.post.domain.dto.*;
import com.kmpc.algobe.community.post.service.PostService;
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
public class PostController {
    private final PostService postService;

    @GetMapping("/{board_id}/posts/notices")
    public ResponseEntity<List<PostListDto>> getNoticePostList(@PathVariable("board_id") Long boardId){
        List<PostListDto> postListDto = postService.findAllNoticePostsByBoardId(boardId);
        return ResponseEntity.status(HttpStatus.OK).body(postListDto);
    }

    @GetMapping("/{board_id}/posts")
    public ResponseEntity<PostListResponseDto> getPostList(@PathVariable("board_id") Long boardId, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                         @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "searchType", required = false) PostSearchType searchType){

        PostListResponseDto postListResponse = postService.findAllPostsByBoardId(boardId, pageable, keyword, searchType);
        return ResponseEntity.status(HttpStatus.OK).body(postListResponse);
    }

    @GetMapping("/{board_id}/posts/questions")
    public ResponseEntity<QuestionListResponseDto> getQuestionList(@PathVariable("board_id") Long boardId, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                   @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "searchType", required = false) PostSearchType searchType){
        QuestionListResponseDto questionListResponseDto = postService.findAllQuestionsByBoardId(boardId, pageable, keyword, searchType);
        return ResponseEntity.status(HttpStatus.OK).body(questionListResponseDto);
    }

    @GetMapping("/{board_id}/posts/{post_id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @CurrentUser User user){
        PostResponseDto responseDto = postService.findPostsByPostId(boardId, postId, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/{board_id}/posts")
    public ResponseEntity<Long> createPost(@PathVariable("board_id") Long boardId, @RequestBody @Valid PostRequestDto requestDto, @CurrentUser User user){
        Long createPostId = postService.createPost(boardId, requestDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPostId);
    }

    @PatchMapping("/{board_id}/posts/{post_id}")
    public ResponseEntity<Long> updatePost(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @RequestBody @Valid PostRequestDto requestDto, @CurrentUser User user){
        Long updatePostId = postService.updatePost(boardId, postId, requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(updatePostId);
    }

    @DeleteMapping("/{board_id}/posts/{post_id}")
    public ResponseEntity<Long> deletePost(@PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @CurrentUser User user){
        Long deletePostId = postService.deletePost(boardId, postId, user);
        return ResponseEntity.status(HttpStatus.OK).body(deletePostId);
    }

    @PostMapping("/{board_id}/posts/{post_id}/adoptions")
    public ResponseEntity<Boolean> questionAdopt(@RequestBody @Valid AdoptRequestDto requestDto, @PathVariable("board_id") Long boardId, @PathVariable("post_id") Long postId, @CurrentUser User user){
        Boolean adoptResult = postService.adoptQuestion(boardId, postId, requestDto, user);
        return null;
    }

}

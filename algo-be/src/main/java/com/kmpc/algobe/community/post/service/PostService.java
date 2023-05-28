package com.kmpc.algobe.community.post.service;

import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.kmpc.algobe.community.board.repository.BoardRepository;
import com.kmpc.algobe.community.post.domain.dto.*;
import com.kmpc.algobe.community.post.domain.entity.Anonymous;
import com.kmpc.algobe.community.post.domain.entity.Post;
import com.kmpc.algobe.community.post.domain.entity.Question;
import com.kmpc.algobe.community.post.domain.entity.QuestionState;
import com.kmpc.algobe.community.post.repository.PostLikeRepository;
import com.kmpc.algobe.community.post.repository.PostRepository;
import com.kmpc.algobe.user.domain.entity.Grade;
import com.kmpc.algobe.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository<Post> postRepository;
    private final PostRepository<Question> questionPostRepository;
    private final PostRepository<Anonymous> anonymousPostRepository;
    private final BoardRepository boardRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional(readOnly = true)
    public PostListResponseDto findAllPostsByBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        Page<PostListDto> listDto = postRepository.findAllByBoardBoardId(boardId, pageable, keyword, searchType);
        Integer pageNum = listDto.getTotalPages();
        return PostListResponseDto
                .builder()
                .postListDto(listDto.stream().toList())
                .boardName(board.getBoardName())
                .pageNum(pageNum)
                .build();
    }

    @Transactional
    public Long createPost(Long boardId, PostRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();

        // 공지사항 여부 확인
        if ((board.getBoardType().equals(BoardType.NOTICE) || requestDto.getNotice()) && !user.getGrade().equals(Grade.ADMIN)) {
            throw new RuntimeException("관리자만 공지할 수 있습니다.");
        }

        // 공지사항이면 일반 게시글로 작성
        if (requestDto.getNotice()) {
            return postRepository.save(Post.builder()
                    .board(board)
                    .user(user)
                    .title(requestDto.getTitle())
                    .content(requestDto.getContent())
                    .isNotice(requestDto.getNotice())
                    .build()).getPostId();
        }

        switch (board.getBoardType()) {
            case ANONYMOUS -> {
                return anonymousPostRepository.save(Anonymous.builder()
                        .board(board)
                        .user(user)
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .isNotice(requestDto.getNotice())
                        .build()).getPostId();
            }
            case QUESTION -> {
                return questionPostRepository.save(Question.builder()
                        .board(board)
                        .user(user)
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .point(requestDto.getPoint())
                        .isNotice(requestDto.getNotice())
                        .state(QuestionState.PROCEEDING)
                        .build()).getPostId();
            }
            default -> {
                return postRepository.save(Post.builder()
                        .board(board)
                        .user(user)
                        .title(requestDto.getTitle())
                        .isNotice(requestDto.getNotice())
                        .content(requestDto.getContent())
                        .build()).getPostId();
            }
        }
    }

    @Transactional
    public Long updatePost(Long boardId, Long postId, PostRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Post post = null;
        if(board.getBoardType().equals(BoardType.QUESTION)){
            post = questionPostRepository.findById(postId).orElseThrow();
        } else {
            post = postRepository.findById(postId).orElseThrow();
        }
        if(!(post.getUser().getUserId().equals(user.getUserId()) || user.getGrade().equals(Grade.ADMIN))){
            throw new RuntimeException("작성자 또는 관리자만 게시글을 수정할 수 있습니다.");
        }
        if ((post.getIsNotice() || requestDto.getNotice()) && !user.getGrade().equals(Grade.ADMIN)){
            throw new RuntimeException("관리자만 공지사항을 관리할 수 있습니다.");
        }
        post.updatePost(requestDto.getTitle(), requestDto.getContent());
        if(requestDto.getNotice() != null)
            post.updateNotice(requestDto.getNotice());
        if(board.getBoardType().equals(BoardType.QUESTION)){
            Question question = (Question) post;
            question.updatePoint(requestDto.getPoint());
        }
        return post.getPostId();
    }

    @Transactional
    public Long deletePost(Long boardId, Long postId, User user) {
        boardRepository.findById(boardId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        if(!(post.getUser().getUserId().equals(user.getUserId()) || user.getGrade().equals(Grade.ADMIN))){
            throw new RuntimeException("작성자 또는 관리자만 게시글을 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
        return post.getPostId();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostsByPostId(Long boardId, Long postId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        Boolean isLike = postLikeRepository.existsPostLikeByPost_PostIdAndUser_UserId(postId, user.getUserId());
        Boolean isAnonymous = board.getBoardType().equals(BoardType.ANONYMOUS);
        return new PostResponseDto(post, isLike, isAnonymous);
    }
}

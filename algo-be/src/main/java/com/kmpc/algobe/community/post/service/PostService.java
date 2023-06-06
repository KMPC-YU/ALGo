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
import com.kmpc.algobe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository<Post> postRepository;
    private final PostRepository<Question> questionPostRepository;
    private final PostRepository<Anonymous> anonymousPostRepository;
    private final BoardRepository boardRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PostListResponseDto findAllPostsByBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        Page<PostListDto> listDto = postRepository.findAllByBoardBoardId(boardId, pageable, keyword, searchType);
        Integer pageNum = (listDto.getTotalPages() == 0) ? 1 : listDto.getTotalPages();
        return PostListResponseDto
                .builder()
                .postListDto(listDto.stream().toList())
                .boardName(board.getBoardName())
                .pageNum(pageNum)
                .build();
    }

    @Transactional(readOnly = true)
    public QuestionListResponseDto findAllQuestionsByBoardId(Long boardId, Pageable pageable, String keyword, PostSearchType searchType) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        Page<QuestionListDto> listDto = questionPostRepository.findAllQuestionByBoardBoardId(boardId, pageable, keyword, searchType);
        Integer pageNum = (listDto.getTotalPages() == 0) ? 1 : listDto.getTotalPages();
        return QuestionListResponseDto
                .builder()
                .questionListDto(listDto.stream().toList())
                .boardName(board.getBoardName())
                .pageNum(pageNum)
                .build();
    }

    public List<PostListDto> findAllNoticePostsByBoardId(Long boardId) {
        boardRepository.findById(boardId).orElseThrow();
        return postRepository.findAllNoticeByBoardId(boardId);
    }

    @Transactional
    public Long createPost(Long boardId, PostRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        // 공지사항 여부 확인
        if ((board.getBoardType().equals(BoardType.NOTICE) || requestDto.getNotice()) && !user.getGrade().equals(Grade.ADMIN)) {
            throw new RuntimeException("관리자만 공지할 수 있습니다.");
        }

//        // 공지사항이면 일반 게시글로 작성
//        if (requestDto.getNotice()) {
//            return postRepository.save(Post.builder()
//                    .board(board)
//                    .user(user)
//                    .title(requestDto.getTitle())
//                    .content(requestDto.getContent())
//                    .isNotice(requestDto.getNotice())
//                    .build()).getPostId();
//        }

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
                // 포인트 체크
                if (user.getPoint() < requestDto.getPoint())
                    throw new RuntimeException("포인트가 부족합니다.");
                else {
                    User updateUser = user.updatePoint(requestDto.getPoint() * -1);
                    userRepository.save(updateUser);
                }

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
        Post post;
        if (board.getBoardType().equals(BoardType.QUESTION)) {
            post = questionPostRepository.findByQuestionId(postId).orElseThrow();
        } else {
            post = postRepository.findByPostId(postId).orElseThrow();
        }
        if (!(post.getUser().getUserId().equals(user.getUserId()) || user.getGrade().equals(Grade.ADMIN))) {
            throw new RuntimeException("작성자 또는 관리자만 게시글을 수정할 수 있습니다.");
        }
        if ((post.getIsNotice() || requestDto.getNotice()) && !user.getGrade().equals(Grade.ADMIN)) {
            throw new RuntimeException("관리자만 공지사항을 관리할 수 있습니다.");
        }
        if (board.getBoardType().equals(BoardType.QUESTION)) {
            Question question = (Question) post;
            if (question.getState().equals(QuestionState.COMPLETE)) {
                throw new RuntimeException("채택된 게시글은 수정할 수 없습니다.");
            }
            if (requestDto.getPoint() - question.getPoint() > user.getPoint())
                throw new RuntimeException("포인트가 부족합니다.");
            question.updatePoint(requestDto.getPoint());
        }
        if (requestDto.getNotice() != null)
            post.updateNotice(requestDto.getNotice());
        post.updatePost(requestDto.getTitle(), requestDto.getContent());
        return post.getPostId();
    }

    @Transactional
    public Long deletePost(Long boardId, Long postId, User user) {
        boardRepository.findById(boardId).orElseThrow();
        Post post = postRepository.findByPostId(postId).orElseThrow();
        if (!(post.getUser().getUserId().equals(user.getUserId()) || user.getGrade().equals(Grade.ADMIN))) {
            throw new RuntimeException("작성자 또는 관리자만 게시글을 삭제할 수 있습니다.");
        }
        postRepository.delete(post);
        return post.getPostId();
    }

    @Transactional(readOnly = true)
    public PostResponseDto findPostsByPostId(Long boardId, Long postId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Post post;
        Integer point = null;
        if (board.getBoardType().equals(BoardType.QUESTION)) {
            Question question = questionPostRepository.findByQuestionId(postId).orElseThrow();
            point = question.getPoint();
            post = question;
        } else {
            post = postRepository.findByPostId(postId).orElseThrow();
        }

        Boolean isLike = postLikeRepository.existsPostLikeByPost_PostIdAndUser_UserId(postId, user.getUserId());

        // 익명게시판이면서 공지사항이 아니면 닉네임 익명
        Boolean isAnonymous = (board.getBoardType().equals(BoardType.ANONYMOUS) && !post.getIsNotice());
        return new PostResponseDto(post, isLike, isAnonymous, point);
    }

    @Transactional
    public Boolean adoptQuestion(Long boardId, Long postId, AdoptRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (!board.getBoardType().equals(BoardType.QUESTION))
            throw new RuntimeException("질문 게시글만 채택할 수 있습니다.");
        Question post = questionPostRepository.findByQuestionId(postId).orElseThrow();
        if (!post.getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("작성자만 질문을 채택할 수 있습니다.");
        }
        if (post.getState().equals(QuestionState.COMPLETE)) {
            throw new RuntimeException("채택된 게시글은 변경할 수 없습니다.");
        }
        return null;
    }
}

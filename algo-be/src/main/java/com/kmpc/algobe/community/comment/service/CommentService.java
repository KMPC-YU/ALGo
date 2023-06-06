package com.kmpc.algobe.community.comment.service;

import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.kmpc.algobe.community.board.repository.BoardRepository;
import com.kmpc.algobe.community.comment.domain.dto.CommentCreateRequestDto;
import com.kmpc.algobe.community.comment.domain.dto.CommentListDto;
import com.kmpc.algobe.community.comment.domain.dto.CommentListResponseDto;
import com.kmpc.algobe.community.comment.domain.dto.CommentUpdateRequestDto;
import com.kmpc.algobe.community.comment.domain.entity.Comment;
import com.kmpc.algobe.community.comment.repository.CommentRepository;
import com.kmpc.algobe.community.post.domain.entity.Anonymous;
import com.kmpc.algobe.community.post.domain.entity.Post;
import com.kmpc.algobe.community.post.repository.PostRepository;
import com.kmpc.algobe.user.domain.entity.Grade;
import com.kmpc.algobe.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final BoardRepository boardRepository;
    private final PostRepository<Post> postRepository;
    private final PostRepository<Anonymous> anonymousPostRepository;
    private final CommentRepository commentRepository;


    @Transactional(readOnly = true)
    public CommentListResponseDto findAllCommentsByPostId(Long boardId, Long postId, Pageable pageable) {
        boardRepository.findById(boardId).orElseThrow();
        Page<CommentListDto> listDto = commentRepository.findAllByPostId(postId, pageable);
        return CommentListResponseDto
                .builder()
                .commentListDto(listDto.stream().toList())
                .pageNum((listDto.getTotalPages() == 0) ? 1 : listDto.getTotalPages())
                .commentNum(listDto.getTotalElements())
                .build();
    }

    @Transactional
    public Long createComment(Long boardId, Long postId, CommentCreateRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if (requestDto.getParent() != null) {
            boolean parentExist = commentRepository.existsById(requestDto.getParent());
            if (!parentExist) {
                throw new RuntimeException();
            }
        }
        String nickname;
        Post post;
        if (board.getBoardType().equals(BoardType.ANONYMOUS)) {
            Anonymous anonymous = anonymousPostRepository.findByAnonymousId(postId).orElseThrow();
            post = anonymous;
            int nickNum = anonymous.getAnonymousCount() + 1;
            Optional<Comment> existComment = commentRepository.findFirstByPostPostIdAndUserUserId(postId, user.getUserId());
            if (existComment.isPresent()) {
                nickname = existComment.get().getNickname();
            } else {
                if (post.getUser().getUserId().equals(user.getUserId())) {
                    nickname = "글쓴이";
                } else {
                    nickname = "익명" + nickNum;
                    anonymous.updateCount();
                }
            }
        } else {
            post = postRepository.findByPostId(postId).orElseThrow();
            nickname = user.getNickname();
        }
        Comment comment = Comment
                .builder()
                .content(requestDto.getContent())
                .user(user)
                .post(post)
                .parent(requestDto.getParent())
                .nickname(nickname)
                .build();

        Long result = commentRepository.save(comment).getCommentId();
        if (result == null) throw new RuntimeException();
        if(board.getBoardType().equals(BoardType.QUESTION) && requestDto.getParent() != null)
            return result;
        post.updateCommentCount(1);
        return result;
    }

    @Transactional
    public Long updateComment(Long boardId, Long postId, Long commentId, CommentUpdateRequestDto requestDto, User user) {
        boardRepository.findById(boardId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getUserId().equals(user.getUserId()) && !user.getGrade().equals(Grade.ADMIN))
            throw new RuntimeException();
        comment.updateComment(requestDto.getContent());
        return comment.getCommentId();
    }

    @Transactional
    public Long deleteComment(Long boardId, Long postId, Long commentId, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        Post post = postRepository.findByPostId(postId).orElseThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getUserId().equals(user.getUserId()) && !user.getGrade().equals(Grade.ADMIN))
            throw new RuntimeException();
        if (comment.getParent() == null) {
            if (commentRepository.existsCommentByParent(comment.getCommentId())) {
                String content = "삭제된 댓글입니다.";
                commentRepository.deleteCommentView(comment.getCommentId(), content, "알수없음");
                return comment.getCommentId();
            } else {
                commentRepository.delete(comment);
            }
        } else {
            Comment parentComment = commentRepository.findById(comment.getParent()).orElseThrow();
            if (parentComment.getIsDeleted()) {
                if (commentRepository.countCommentByParent(parentComment.getCommentId()) == 1) {
                    commentRepository.deleteCommentsByCommentIdAndParent(comment.getParent());
                    // 질문 게시판은 대댓글 개수 체크 X
                    if(board.getBoardType().equals(BoardType.QUESTION))
                        post.updateCommentCount(-1);
                    else
                        post.updateCommentCount(-2);
                    return comment.getCommentId();
                } else
                    commentRepository.delete(comment);
            } else {
                commentRepository.delete(comment);
            }
        }
        if(board.getBoardType().equals(BoardType.QUESTION) && comment.getParent() != null)
            return comment.getCommentId();

        post.updateCommentCount(-1);
        return comment.getCommentId();
    }
}

package com.kmpc.algobe.community.post.service;

import com.kmpc.algobe.community.board.repository.BoardRepository;
import com.kmpc.algobe.community.post.domain.entity.Post;
import com.kmpc.algobe.community.post.domain.entity.PostLike;
import com.kmpc.algobe.community.post.repository.PostLikeRepository;
import com.kmpc.algobe.community.post.repository.PostRepository;
import com.kmpc.algobe.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final BoardRepository boardRepository;
    private final PostRepository<Post> postRepository;

    @Transactional
    public Long createPostLike(Long boardId, Long postId, User user) {
        boardRepository.findById(boardId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        if(postLikeRepository.existsPostLikeByPost_PostIdAndUser_UserId(postId, user.getUserId())){
            throw new RuntimeException("이미 추천한 게시글입니다.");
        }
        post.updateLikeCount(1);
        return postLikeRepository.save(PostLike
                .builder()
                .post(post)
                .user(user)
                .build()).getPostLikeId();
    }

    @Transactional
    public Long deletePostLike(Long boardId, Long postId, User user){
        boardRepository.findById(boardId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();
        PostLike postLike = postLikeRepository.findPostLikeByPostPostIdAndUserUserId(postId, user.getUserId()).orElseThrow();
        post.updateLikeCount(-1);
        postLikeRepository.delete(postLike);
        return postLike.getPostLikeId();
    }
}

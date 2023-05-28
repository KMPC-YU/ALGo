package com.kmpc.algobe.community.post.repository;

import com.kmpc.algobe.community.post.domain.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Boolean existsPostLikeByPost_PostIdAndUser_UserId(Long postId, Long userId);
}

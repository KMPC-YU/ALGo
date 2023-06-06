package com.kmpc.algobe.community.comment.repository;

import com.kmpc.algobe.community.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom{
    Optional<Comment> findFirstByPostPostIdAndUserUserId(Long postId, Long userId);
    Boolean existsCommentByParent(Long parent);

    //@Modifying(clearAutomatically = true)
    @Modifying
    @Query("update Comment set content = :content, nickname = :nickname, isDeleted = true where commentId = :commentId")
    void deleteCommentView(@Param(value = "commentId") Long commentId, @Param(value = "content") String content, @Param(value = "nickname") String nickname);

    @Query("select count(c) from Comment c where c.parent = :parentId")
    Long countCommentByParent(@Param(value = "parentId") Long parentId);

    @Modifying
    @Query("delete from Comment c where c.parent = :id or c.commentId = :id")
    void deleteCommentsByCommentIdAndParent(@Param(value = "id")Long parent);
}

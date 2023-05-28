package com.kmpc.algobe.community.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kmpc.algobe.community.post.domain.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String author;
    @JsonProperty("comment_count")
    private Integer commentCount;
    @JsonProperty("like_count")
    private Integer likeCount;
    @JsonProperty("view_count")
    private Long viewCount;
    @JsonProperty("is_like")
    private Boolean isLike;
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    public PostResponseDto(Post post, Boolean isLike, Boolean isAnonymous){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = isAnonymous ? "익명" : post.getUser().getNickname();
        this.commentCount = post.getCommentCount();
        this.likeCount = post.getLikeCount();
        this.isLike = isLike;
        this.viewCount = post.getViewCount();
        this.createdAt = post.getCreatedAt();
    }
}

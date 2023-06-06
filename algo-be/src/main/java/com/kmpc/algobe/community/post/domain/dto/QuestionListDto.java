package com.kmpc.algobe.community.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionListDto {
    private Long id;
    private String title;
    private String author;
    private Integer point;

    @JsonProperty("view_count")
    private Long viewCount;

    @JsonProperty("like_count")
    private Integer likeCount;

    @JsonProperty("comment_count")
    private Integer commentCount;

    @JsonProperty("notice")
    private Boolean notice;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @QueryProjection
    public QuestionListDto(Long id, String title, String author, Integer point, Long viewCount, Integer likeCount, Integer commentCount, Boolean notice, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.point = point;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.notice = notice;
        this.createdAt = createdAt;
    }
}

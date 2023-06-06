package com.kmpc.algobe.community.comment.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentListResponseDto {
    private List<CommentListDto> commentListDto;

    @JsonProperty("page_num")
    private Integer pageNum;

    @JsonProperty("comment_num")
    private Long commentNum;

    @Builder
    public CommentListResponseDto(List<CommentListDto> commentListDto, Integer pageNum, Long commentNum) {
        this.commentListDto = commentListDto;
        this.pageNum = pageNum;
        this.commentNum = commentNum;
    }
}

package com.kmpc.algobe.community.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListResponseDto {
    private List<PostListDto> postListDto;
    @JsonProperty("board_name")
    private String boardName;

    @JsonProperty("page_num")
    private Integer pageNum;

    @Builder
    public PostListResponseDto(List<PostListDto> postListDto, String boardName, Integer pageNum) {
        this.postListDto = postListDto;
        this.boardName = boardName;
        this.pageNum = pageNum;
    }
}

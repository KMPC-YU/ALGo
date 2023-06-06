package com.kmpc.algobe.community.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionListResponseDto {
    private List<QuestionListDto> questionListDto;

    @JsonProperty("board_name")
    private String boardName;

    @JsonProperty("page_num")
    private Integer pageNum;

    @Builder
    public QuestionListResponseDto(List<QuestionListDto> questionListDto, String boardName, Integer pageNum) {
        this.questionListDto = questionListDto;
        this.boardName = boardName;
        this.pageNum = pageNum;
    }
}

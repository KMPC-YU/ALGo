package com.kmpc.algobe.community.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {
    @JsonProperty("board_name")
    private String boardName;
    @JsonProperty("board_type")
    private String boardType;

    @Builder
    public BoardResponseDto(String boardName, String boardType) {
        this.boardName = boardName;
        this.boardType = boardType;
    }
}

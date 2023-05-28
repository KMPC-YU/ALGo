package com.kmpc.algobe.community.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardListDto {
    @JsonProperty("board_id")
    private Long boardId;
    private String name;
    private String type;


    @QueryProjection
    public BoardListDto(Long boardId, String name, BoardType type){
        this.boardId = boardId;
        this.name = name;
        this.type = type.toString();
    }
}

package com.kmpc.algobe.community.board.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kmpc.algobe.community.board.domain.entity.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCreateRequestDto {
    @NotBlank
    @JsonProperty("board_name")
    private String boardName;

    @NotNull
    @JsonProperty("board_type")
    private BoardType boardType;
}

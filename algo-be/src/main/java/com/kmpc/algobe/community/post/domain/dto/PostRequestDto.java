package com.kmpc.algobe.community.post.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @JsonProperty("notice")
    @NotNull
    private Boolean notice;

    private Integer point;
}

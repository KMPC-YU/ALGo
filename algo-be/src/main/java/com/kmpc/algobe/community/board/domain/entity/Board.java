package com.kmpc.algobe.community.board.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(name = "board_name")
    private String boardName;

    @Column(name = "board_type")
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @Builder
    public Board(String boardName, BoardType boardType){
        this.boardName = boardName;
        this.boardType = boardType;
    }

    public Board updateBoard(String boardName){
        if(boardName != null && !boardName.isEmpty())
            this.boardName = boardName;
        return this;
    }
}

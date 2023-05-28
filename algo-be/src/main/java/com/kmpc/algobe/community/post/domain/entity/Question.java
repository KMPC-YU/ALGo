package com.kmpc.algobe.community.post.domain.entity;

import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;


@Entity
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DiscriminatorValue("QUESTION")
public class Question extends Post{
    @NotNull
    private Integer point;

    @Enumerated(EnumType.STRING)
    private QuestionState state;

    public Question(Board board, User user, String title, String content, Boolean isNotice, Integer point, QuestionState state) {
        super(board, user, title, content, isNotice);
        this.point = point;
        this.state = state;
    }

    public void updatePoint(Integer point){
        this.point = point;
    }
}

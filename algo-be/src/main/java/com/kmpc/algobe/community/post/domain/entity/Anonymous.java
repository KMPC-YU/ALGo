package com.kmpc.algobe.community.post.domain.entity;

import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@DynamicInsert
@DiscriminatorValue("ANONYMOUS")
public class Anonymous extends Post{
    @Column(name = "anonymous_count")
    @ColumnDefault("0")
    private Integer anonymousCount;

    public Anonymous(Board board, User user, String title, String content, Boolean isNotice) {
        super(board, user, title, content, isNotice);
    }

    public void updateCount(){
        this.anonymousCount++;
    }

}

package com.kmpc.algobe.community.post.domain.entity;

import com.kmpc.algobe.BaseTimeEntity;
import com.kmpc.algobe.community.board.domain.entity.Board;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@SuperBuilder
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public class Post extends BaseTimeEntity{
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Board board;

    @ManyToOne
    @JoinColumn(name = "author")
    @NotNull
    private User user;

    @Size(min = 1, max = 50)
    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotNull
    @Size(min = 1)
    private String content;

    @Column(name = "like_count")
    @ColumnDefault("0")
    private Integer likeCount;

    @Column(name = "comment_count")
    @ColumnDefault("0")
    private Integer commentCount;

    @Column(name = "view_count")
    @ColumnDefault("0")
    private Long viewCount;

    @Column(name = "report_count")
    @ColumnDefault("0")
    private Integer reportCount;

    @Column(name = "is_notice")
    private Boolean isNotice;

    public Post(Board board, User user, String title, String content, Boolean isNotice) {
        this.board = board;
        this.user = user;
        this.title = title;
        this.content = content;
        this.isNotice = isNotice;
    }

    public void updatePost(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void updateNotice(Boolean isNotice){
        this.isNotice = isNotice;
    }

    public void updateCommentCount(int symbol){
        this.commentCount += symbol;
    }

    public void updateLikeCount(int symbol) {this.likeCount += symbol;}
}

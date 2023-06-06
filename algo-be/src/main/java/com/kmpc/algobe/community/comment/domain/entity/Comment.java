package com.kmpc.algobe.community.comment.domain.entity;

import com.kmpc.algobe.BaseTimeEntity;
import com.kmpc.algobe.community.post.domain.entity.Post;
import com.kmpc.algobe.user.domain.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Post post;

    @ManyToOne
    @JoinColumn(name = "author")
    @NotNull
    private User user;

    @NotNull
    @Size(min = 1)
    private String nickname;

    @Column(columnDefinition = "TEXT")
    @Size(min = 1)
    @NotNull
    private String content;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    @NotNull
    private Boolean isDeleted;

    @Column(name = "parent_id")
    private Long parent;


    @Builder
    private Comment(String content, User user, Post post, Long parent, String nickname) {
        this.content = content;
        this.user = user;
        this.post = post;
        this.parent = parent;
        this.nickname = nickname;
        this.isDeleted = false;
    }

    public void updateComment(String content) {
        this.content = content;
    }
}

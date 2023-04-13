package com.kmpc.algobe.user.domain.entity;

import com.kmpc.algobe.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "USER")
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 30, unique = true)
    @NotNull
    private String username;

    @NotNull
    private String password;

    @Column(length = 30, unique = true)
    @NotNull
    private String nickname;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(name = "login_type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(name = "user_grade")
    @NotNull
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Column(name = "profile_img")
    private String profileImg;

    @Builder
    public User(String username, String password, String nickname, String email, LoginType loginType, Grade grade) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.loginType = loginType;
        this.grade = grade;
    }

    public User updateGrade(Grade grade) {
        if (grade != null)
            this.grade = grade;
        return this;
    }

    public User updatePassword(String password) {
        if (password != null)
            this.password = password;
        return this;
    }

    public User updateNickname(String nickname) {
        if(nickname != null)
            this.nickname = nickname;
        return this;
    }

    public User updateProfileImage(String profileImageUrl){
        if(profileImageUrl != null)
            this.profileImg = profileImageUrl;
        return this;
    }
}

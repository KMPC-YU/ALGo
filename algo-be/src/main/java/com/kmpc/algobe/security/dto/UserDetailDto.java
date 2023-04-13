package com.kmpc.algobe.security.dto;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserDetailDto extends User {
    private com.kmpc.algobe.user.domain.entity.User user;

    public UserDetailDto(com.kmpc.algobe.user.domain.entity.User user){
        super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getGrade().name())));
        this.user = user;
    }
}

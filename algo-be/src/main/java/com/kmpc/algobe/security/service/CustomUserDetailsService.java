package com.kmpc.algobe.security.service;

import com.kmpc.algobe.security.dto.UserDetailDto;
import com.kmpc.algobe.user.domain.entity.User;
import com.kmpc.algobe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        UserDetailDto userDetailDto = new UserDetailDto(user);

        return userDetailDto;
    }
}
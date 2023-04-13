package com.kmpc.algobe.user.service;

import com.kmpc.algobe.security.provider.JwtProvider;
import com.kmpc.algobe.user.domain.dto.LoginRequestDto;
import com.kmpc.algobe.user.domain.dto.SignUpRequestDto;
import com.kmpc.algobe.user.domain.entity.LoginType;
import com.kmpc.algobe.user.domain.entity.User;
import com.kmpc.algobe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;

    @Transactional
    public Boolean signUp(SignUpRequestDto signUpRequestDto) {
        // TODO 이메일 인증 확인 후 로직 처리
        log.info("password = {}, passwordConfirm = {}", signUpRequestDto.getPassword(), signUpRequestDto.getPasswordConfirm());
        if (!signUpRequestDto.getPassword().equals(signUpRequestDto.getPasswordConfirm())) {
            throw new RuntimeException("비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
        }

        userRepository.findByUsername(signUpRequestDto.getUsername()).ifPresent(user -> {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        });

        userRepository.findByNickname(signUpRequestDto.getNickname()).ifPresent(user -> {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        });

        userRepository.findByEmail(signUpRequestDto.getEmail()).ifPresent(user -> {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        });

        User user = signUpRequestDto.toUserEntity(encoder);
        userRepository.save(user);

        return true;
    }

    public String login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));
        if(!encoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        return jwtProvider.generateToken(user.getUsername(), user.getNickname(), user.getGrade().name());
    }
}

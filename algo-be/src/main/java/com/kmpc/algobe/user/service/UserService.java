package com.kmpc.algobe.user.service;

import com.kmpc.algobe.allergy.AllergyType;
import com.kmpc.algobe.redis.util.RedisUtil;
import com.kmpc.algobe.security.provider.JwtProvider;
import com.kmpc.algobe.user.domain.dto.LoginRequestDto;
import com.kmpc.algobe.security.dto.LoginResponseDto;
import com.kmpc.algobe.user.domain.dto.PasswordChangeDto;
import com.kmpc.algobe.user.domain.dto.PasswordFindDto;
import com.kmpc.algobe.user.domain.dto.SignUpRequestDto;
import com.kmpc.algobe.user.domain.entity.User;
import com.kmpc.algobe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final RedisUtil redisUtil;

    @Transactional
    public Boolean signUp(SignUpRequestDto signUpRequestDto) {
        if (!redisUtil.getData(signUpRequestDto.getEmail() + "_validate").equals("True"))
            throw new RuntimeException("인증번호가 일치하지 않습니다.");

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

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));
        if (!encoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        return jwtProvider.generateToken(user.getUsername(), user.getNickname(), user.getGrade().name());
    }

    @Transactional(readOnly = true)
    public Boolean existsByNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    @Transactional(readOnly = true)
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Transactional
    public Long changeUserAllergy(List<AllergyType> allergyTypeList, User user) {
        Integer allergyInfo = allergyTypeList.stream()
                .mapToInt(AllergyType::getAllergyBit)
                .sum();

        return userRepository.save(user.updateAllergyInfo(allergyInfo)).getUserId();
    }

    @Transactional
    public Long changeProfileImage(String imageUrl, User user) {
        return userRepository.save(user.updateProfileImage(imageUrl)).getUserId();
    }

    @Transactional
    public Long changeNickname(String nickname, User user) {
        return userRepository.save(user.updateNickname(nickname)).getUserId();
    }

    @Transactional
    public Long changePassword(PasswordChangeDto passwordChangeDto, User user) {
        if (!passwordChangeDto.getNewPassword().equals(passwordChangeDto.getNewPasswordConfirm())) {
            throw new RuntimeException("비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
        }

        if (!user.getPassword().equals(encoder.encode(passwordChangeDto.getCurrentPassword()))) {
            throw new RuntimeException("현재 비밀번호가 일치하지 않습니다.");
        }

        if (user.getPassword().equals(encoder.encode(passwordChangeDto.getNewPassword()))) {
            throw new RuntimeException("이전 비밀번호와 같은 비밀번호를 사용할 수 없습니다.");
        }

        return userRepository.save(user.updatePassword(encoder.encode(passwordChangeDto.getNewPassword()))).getUserId();
    }

    @Transactional
    public String findPassword(PasswordFindDto passwordFindDto) {
        User user = userRepository.findByEmail(passwordFindDto.getEmail()).orElseThrow();

        if (!redisUtil.getData(passwordFindDto.getEmail() + "_validate").equals("True"))
            throw new RuntimeException("인증번호가 일치하지 않습니다.");

        if (!passwordFindDto.getNewPassword().equals(passwordFindDto.getNewPasswordConfirm())) {
            throw new RuntimeException("비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
        }

        if (user.getPassword().equals(encoder.encode(passwordFindDto.getNewPassword()))) {
            throw new RuntimeException("이전 비밀번호와 같은 비밀번호를 사용할 수 없습니다.");
        }

        return userRepository.save(user.updatePassword(encoder.encode(passwordFindDto.getNewPassword()))).getEmail();
    }
}

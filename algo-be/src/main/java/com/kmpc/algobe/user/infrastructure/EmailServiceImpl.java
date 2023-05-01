package com.kmpc.algobe.user.infrastructure;

import com.kmpc.algobe.redis.util.RedisUtil;
import com.kmpc.algobe.user.domain.dto.EmailCodeDto;
import com.kmpc.algobe.user.domain.dto.ResultVerifyCode;
import com.kmpc.algobe.user.domain.entity.User;
import com.kmpc.algobe.user.repository.UserRepository;
import com.kmpc.algobe.user.service.EmailService;
import com.kmpc.algobe.user.util.RandomCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    private static final Long expireTime = 600L;

    @Override
    public void sendEmail(String email, String username) {
        if (username == null) {
            Boolean userExist = userRepository.existsByEmail(email);
            if (userExist)
                throw new RuntimeException("이미 존재하는 이메일입니다.");
        } else {
            User user = userRepository.findByEmail(email).orElseThrow();
            if (!user.getUsername().equals(username))
                throw new RuntimeException("존재하지 않는 아이디입니다.");
        }

        if (redisUtil.existData(email)) {
            redisUtil.deleteData(email);
            redisUtil.deleteData(email + "_count");
        }

        if (redisUtil.existData(email + "_validate"))
            redisUtil.deleteData(email + "_validate");

        String code = RandomCodeUtil.createCode();

        SimpleMailMessage message = new SimpleMailMessage();

        String content = "ALGo 인증요청\n" +
                email +
                "님의 인증요청 코드\n" +
                code;

        message.setTo(email);
        message.setSubject("[ALGo] 이메일 인증 코드");
        message.setText(content);

        String emailCount = email + "_count";

        redisUtil.setDataExpire(email, code, expireTime);
        redisUtil.setDataExpire(emailCount, String.valueOf(0), expireTime);
        javaMailSender.send(message);
    }

    @Override
    public ResultVerifyCode verifyEmailCode(EmailCodeDto emailCodeDto) {
        String email = emailCodeDto.getEmail();
        String code = emailCodeDto.getCode();
        String codeFoundByEmail = redisUtil.getData(email);
        String emailCount = email + "_count";
        int countFoundByEmail = Integer.parseInt(redisUtil.getData(emailCount));
        if (codeFoundByEmail == null)
            return ResultVerifyCode.TIME_OUT;
        if (countFoundByEmail > 4)
            return ResultVerifyCode.ATTEMPT_EXCEED;
        if (!codeFoundByEmail.equals(code)) {
            redisUtil.setDataExpire(emailCount, String.valueOf(countFoundByEmail + 1), expireTime);
            return ResultVerifyCode.INVALID;
        } else {
            redisUtil.deleteData(email);
            redisUtil.deleteData(emailCount);
            redisUtil.setDataExpire(email+"_validate", "True", expireTime);
            return ResultVerifyCode.VALID;
        }
    }
}

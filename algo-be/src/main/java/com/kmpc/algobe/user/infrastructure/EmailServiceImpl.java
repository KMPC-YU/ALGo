package com.kmpc.algobe.user.infrastructure;

import com.kmpc.algobe.redis.util.RedisUtil;
import com.kmpc.algobe.user.domain.dto.EmailCodeDto;
import com.kmpc.algobe.user.domain.dto.ResultVerifyCode;
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

    @Override
    public void sendEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        });
        String code = RandomCodeUtil.createCode();
        long expireTime = 600L; // 10분

        SimpleMailMessage message = new SimpleMailMessage();

        StringBuilder content = new StringBuilder();
        content.append("ALGo 인증요청\n");
        content.append(email);
        content.append("님의 인증요청 코드\n");
        content.append(code);

        message.setTo(email);
        message.setSubject("[ALGo] 이메일 인증 코드");
        message.setText(content.toString());

        String emailCount = email + "_count";

        redisUtil.setDataExpire(email, code, expireTime);
        redisUtil.setData(emailCount, String.valueOf(0));
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
        if (codeFoundByEmail.equals(code))
            return ResultVerifyCode.VALID;
        else{
            redisUtil.setData(emailCount, String.valueOf(countFoundByEmail + 1));
            return ResultVerifyCode.INVALID;
        }
    }
}

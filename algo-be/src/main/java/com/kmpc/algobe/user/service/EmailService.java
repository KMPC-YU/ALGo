package com.kmpc.algobe.user.service;

import com.kmpc.algobe.user.domain.dto.EmailCodeDto;
import com.kmpc.algobe.user.domain.dto.ResultVerifyCode;

public interface EmailService {
    void sendEmail(String email);

    ResultVerifyCode verifyEmailCode(EmailCodeDto emailCodeDto);
}

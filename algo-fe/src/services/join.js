import { instance } from './index.js'

// 아이디 중복확인
export function usernameDuplicateCheck(username) {
    return instance.get('/api/v1/verify-username?username=' + username)
}

// 닉네임 중복확인
export function nicknameDuplicateCheck(nickname) {
    return instance.get('/api/v1/verify-nickname?nickname=' + nickname)
}

// 이메일 중복확인
export function emailDuplicateCheck(email) {
    return instance.get('/api/v1/verify-email?email=' + email)
}

// 이메일 인증코드 요청
export function sendVerifyEmailCode(email) {
    return instance.post('/api/v1/emails', {
        email: email,
        username: null,
    })
}

// 이메일 인증코드 검증
export function verifyEmailCode(email, code) {
    return instance.post('/api/v1/validate', {
        email: email,
        code: code,
    })
}

// 회원가입
export function signUp(userData) {
    return instance.post('/api/v1/signUp', {
        username: userData.username,
        password: userData.password,
        email: userData.email,
        code: userData.code,
        nickname: userData.nickname,
        password_confirm: userData.password_confirm,
        allergy_type: userData.allergy_type,
    })
}
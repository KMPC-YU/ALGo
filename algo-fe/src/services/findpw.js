import { instance } from './index.js'

// 아이디 중복확인
export function usernameDuplicateCheck(username) {
    return instance.get(`/api/v1/verify-username?username=${username}`)
}

// 이메일 중복확인
export function emailDuplicateCheck(email) {
    return instance.get(`/api/v1/verify-email?email=${email}`)
}

// 이메일 인증코드 요청
export function sendVerifyEmailCode(email, username) {
    return instance.post('/api/v1/emails', {
        email: email,
        username: username,
    })
}

// 이메일 인증코드 검증
export function verifyEmailCode(email, code) {
    return instance.post('/api/v1/validate', {
        email: email,
        code: code,
    })
}

// 비밀번호 변경
export function changePassword(username, email, pw, pw2) {
    return instance.patch(`api/v1/users/${username}/passwords`, {
        email: email,
        new_password: pw,
        new_password_confirm: pw2,
    })
}
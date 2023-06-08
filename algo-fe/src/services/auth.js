import { instance } from './index.js'

// 로그인
export function login(loginData) {
    return instance.post('/api/v1/login', {
        username: loginData.username,
        password: loginData.password,
    })
}

// 로그아웃
export function logout() {
    return instance.post('/api/v1/logout', {})
}
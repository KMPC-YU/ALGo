import { instance } from './index.js'

// 사용자 알레르기 정보
export function getUserAllergy() {
  return instance.get('/api/v1/users/allergies')
}

// 식품목록 요청
export function getFoodList(page, sort, keyword, allergyParams) {
  return instance.get(`/api/v1/foods?page=${page}&size=12&sort=${sort}&keyword=${keyword}`, { allergyParams })
}

// 최근 본 식품
export function getRecentFood() {
  return instance.get('/api/v1/foods/viewLists')
}

// 추천 식품
export function getRecommendFood() {
  return instance.get('/api/v1/foods/recommendation')
}

// 관리자 권한 확인
export function isAdmin() {
  return instance.get('/api/v1/admin')
}

// 식품 즐겨찾기
export function favorite(isLike, foodId) {
  return isLike
    ? instance.delete(`/api/v1/foods/${foodId}/likes`)
    : instance.post(`/api/v1/foods/${foodId}/likes`, {})
}
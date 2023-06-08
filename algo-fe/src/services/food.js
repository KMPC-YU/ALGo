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

// 식품 추가
export function createFood(foodData) {
  return instance.post('/api/v1/foods', {
    code: foodData.code,
    nutrition: foodData.nutrition,
    allergy : foodData.allergy,
    food_name : foodData.food_name,
    raw_materials : foodData.raw_materials,
    product_kind : foodData.product_kind,
    food_image_url : foodData.food_image_url,
  })
}

// 식품 상세정보 요청
export function getFood(foodId) {
  return instance.get(`/api/v1/foods/${foodId}`)
}

// 식품 알러지 정보 요청
export function getFoodAllergies(foodId) {
  return instance.get(`/api/v1/foods/${foodId}/allergies`)
}

// 식품 상세 정보 수정
export function updateFood(foodId, foodData) {
  return instance.patch(`/api/v1/foods/${foodId}`, {
    code: foodData.code,
    nutrition: foodData.nutrition,
    allergy : foodData.allergy,
    food_name : foodData.food_name,
    raw_materials : foodData.raw_materials,
    product_kind : foodData.product_kind,
    food_image_url : foodData.food_image_url,
  })
}

// 식품 삭제
export function deleteFood(foodId) {
  return instance.delete(`/api/v1/foods/${foodId}`)
}
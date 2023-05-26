<template>
  <div class="row">
    <div class="col-1">
    </div>
    <div class="col-10 mt-4">
      <div class="card">
        <div class="card-body">
          <h4 v-if="!editable" class="card-title">새로운 식품 등록</h4>
          <h4 v-else class="card-title">식품 수정</h4>
          <div class="row">
            <div class="col-4">
              <div class="card">
                <img v-if="previewImage === ''" src="../../assets/defaultImage.png" alt="...">
                <img v-else :src=previewImage alt=""/>
            </div>
              <div class="input-group mt-3">
                <span class="input-group-text" >이미지 URL</span>
                <input type="text" class="form-control" v-model="imageURL" @keyup.enter="changeImage">
              </div>
              <button class="btn btn-primary mt-2" @click="changeImage">이미지 미리보기</button>
            </div>
            <div class="col-8">
              <div class="row">
                <div class="col-3">
                  <h5 class="form-label">식품명</h5>
                </div>
                <div class="col-9">
                  <input type="text" class="form-control" id="food_name" v-model="foodName">
                </div>
              </div>
              <div class="row mt-4">
                <div class="col-3">
                  <h5 class="form-label">식품보고번호</h5>
                </div>
                <div class="col-9">
                  <input type="text" class="form-control" id="food_report_number" v-model="foodCode">
                </div>
              </div>
              <div class="row mt-4">
                <div class="col-3">
                  <h5 class="form-label">식품 종류</h5>
                </div>
                <div class="col-9">
                  <input type="text" class="form-control" id="product_kind" v-model="foodKind">
                </div>
              </div>
              <div class="row mt-4">
                <div class="col-3">
                  <h5 class="form-label">영양 성분</h5>
                </div>
                <div class="col-9">
                  <input type="text" class="form-control" id="nutrition" v-model="foodNutirition">
                </div>
              </div>
              <div class="row mt-4">
                <div class="col-3">
                  <h5 class="form-label">원재료명</h5>
                </div>
                <div class="col-9">
                  <input type="text" class="form-control" id="raw_materials" v-model="foodRawMeterials">
                </div>
              </div>

              <div class="mb-3 mt-4">
                <div class="card p-3 mb-3 bg-body">
                  <h5>알레르기 정보</h5>
                  <div>
                    <div class="form-check form-check-inline" v-for="data in allergyCheckData" :key="data.id">
                      <input class="form-check-input" type="checkbox" v-model="data.selected" :id="`${data.name}`" :value="`${data.name}`">
                      <span class="form-check-label">{{ data.foodName }}</span>
                    </div>
                  </div>
                </div>
              </div>

              <div>
                <button type="button" class="btn btn-primary" @click="onSave" style=" float: right;margin-left: 5px;">저장</button>
                <button type="button" class="btn btn-primary" @click="moveToFoodList" style=" float: right;">목록</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, watch, onMounted } from 'vue'
import router from '@/router'
import { useRoute } from 'vue-router'
import * as FoodAPI from '@/services/food.js'

export default {
  setup() {
    const allergyData = new Map()
    const route = useRoute()
    const editable = ref(route.query.editable)
    const foodId = route.query.id
    const imageURL = ref('')
    const previewImage = ref('')

    const changeImage = () => {
      previewImage.value = imageURL.value
    }

    watch(imageURL, () => {
      if (imageURL.value === '')  previewImage.value = ''
    })

    const allergyCheckData = ref([  // 현재 사용자가 선택한 알레르기 데이터
      {id: 1, name: 'squid', foodName: '오징어', selected: false},
      {id: 2, name: 'eggs', foodName: '난류', selected: false},
      {id: 3, name: 'chicken', foodName: '닭', selected: false},
      {id: 4, name: 'wheat', foodName: '밀', selected: false},
      {id: 5, name: 'nuts', foodName: '견과류', selected: false},
      {id: 6, name: 'milk', foodName: '우유', selected: false},
      {id: 7, name: 'pork', foodName: '돼지고기', selected: false},
      {id: 8, name: 'beef', foodName: '소고기', selected: false},
      {id: 9, name: 'clams', foodName: '조개류', selected: false},
      {id: 10, name: 'sulphite', foodName: '아황산류', selected: false},
      {id: 11, name: 'buckwheat', foodName: '메밀', selected: false},
      {id: 12, name: 'crab', foodName: '게', selected: false},
      {id: 13, name: 'shrimp', foodName: '새우', selected: false},
      {id: 14, name: 'soybean', foodName: '대두', selected: false},
      {id: 15, name: 'tomato', foodName: '토마토', selected: false},
      {id: 16, name: 'fish', foodName: '생선', selected: false},
      {id: 17, name: 'sesame', foodName: '참깨', selected: false},
      {id: 18, name: 'fruit', foodName: '과일', selected: false},
      {id: 19, name: 'garlic', foodName: '마늘', selected: false},
      {id: 20, name: 'vegetable', foodName: '채소', selected: false},
    ])

    const foodData = ref({
      raw_materials: '',
      allergy: '',
      food_image_url: '',
      code: '',
      food_name: '',
      is_like: '',
      like_count: '',
      nutrition: '',
      product_kind: '',
    })

    const foodName = ref('')
    const foodCode = ref('')
    const foodKind = ref('')
    const foodNutirition = ref('')
    const foodRawMeterials = ref('')

    watch(allergyCheckData.value, () => {
      checkSelected()
    })

    const checkSelected = () => {
      for (let i = 0; i < allergyCheckData.value.length; i++) {
        allergyData.set(allergyCheckData.value[i].name, allergyCheckData.value[i].selected)
      }
    }

    const getFoodData = () => {
      FoodAPI.getFood(foodId)
        .then((res) => {
          foodName.value = res.data.food_name
          foodCode.value = res.data.code
          foodKind.value = res.data.product_kind
          foodNutirition.value = res.data.nutrition
          foodRawMeterials.value = res.data.raw_materials
        })
        .catch(err => console.error(err))

      FoodAPI.getFoodAllergies(foodId)
        .then((res) => {
          const allergyData = new Map(Object.entries(res.data))
          for (let i = 0; i < allergyData.size; i++) {
            allergyCheckData.value[i].selected = allergyData.get(allergyCheckData.value[i].name)
          }
          setParams()
        })
        .catch(err => console.error(err))
    }

    let params = new URLSearchParams()
    const setParams = () => {
      params = new URLSearchParams()
      for (let i = 0; i < allergyCheckData.value.length; i++) {
        params.append(allergyCheckData.value[i].name, allergyCheckData.value[i].selected)
      }
    }

    const onSave = () => {
      if (editable.value) {
        FoodAPI.updateFood(foodId, {
          code: foodCode.value,
          nutrition : foodNutirition.value,
          allergy : Object.fromEntries(allergyData),
          food_name : foodName.value,
          raw_materials : foodRawMeterials.value,
          product_kind : foodKind.value,
          food_image_url : previewImage.value,
        })
        .then(() => {
          alert('식품 수정이 완료되었습니다!')
          router.push({ name: 'FoodList' })
        })
        .catch(() => {
          alert('작성한 내용을 다시 확인해주세요.\n식품 보고번호는 숫자만 가능합니다.')
        })
      } else {
        FoodAPI.createFood({
          code: foodCode.value,
          nutrition : foodNutirition.value,
          allergy : Object.fromEntries(allergyData),
          food_name : foodName.value,
          raw_materials : foodRawMeterials.value,
          product_kind : foodKind.value,
          food_image_url : previewImage.value,
        })
        .then(() => {
          alert('식품 추가가 완료되었습니다!')
          router.push({name: 'FoodList'})
        })
        .catch(() => {
          alert('작성한 내용을 다시 확인해주세요.\n식품 보고번호는 숫자만 가능합니다.')
        })
      }
    }

    const moveToFoodList = () => {
      router.push('/foods')
    }

    onMounted(() => {
      if (editable.value) {
        getFoodData()
      }
    })

    return {
      allergyCheckData,
      foodName,
      foodCode,
      foodKind,
      foodNutirition,
      foodRawMeterials,
      onSave,
      moveToFoodList,
      editable,
      foodData,
      imageURL,
      changeImage,
      previewImage,
    }
  }
}
</script>

<style scoped>

</style>
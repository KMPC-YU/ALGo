<template>
  <div class="container">
    <div class="row">

      <!--최근 본 식품-->
      <div class="col-2 mt-4">
        <div class="accordion position-sticky" style="top: 2rem;">
          <div class="accordion-item">
            <div class="accordion-header">
              <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#leftMenu">
                최근 본 식품
              </button>
            </div>
            <div id="leftMenu" class="accordion-collapse collapse show" style="width: 110%">
              <div class="accordion-body">
                <ul class="list-group list-group-flush text-center"
                  style=" max-height: 800px;
                  overflow-y:auto;">
                  <li v-for="viewFood in viewFoodList" :key="viewFood.id" class="list-group-item">
                    <img :src="viewFood.food_image_url" height="100" width="100" alt=""/><br/>
                    <router-link :to="{ name: 'FoodView', params: { food_id: viewFood.id } }" class="food-name stretched-link">{{ viewFood.food_name }}</router-link>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!--식품정보-->
      <div class="col-8 card mt-4 shadow p-3 mb-5 bg-body">
        <div class="card-body">
          <h4 class="col-10 card-title mb-4">식품 정보</h4>

          <div class="input-group mb-3">
            <input type="search" class="form-control" placeholder="식품명을 입력하세요." v-model="searchText" @keyup.enter="searchFood">
            <button type="button" class="btn btn-primary" @click="searchFood">
              <i class="bi bi-search"></i>
            </button>
          </div>

          <div class="mb-3">
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
          <div class="row">
            <div class="col-2">
              <select v-model="selectedSort" class="form-select">
                <option value="foodId,DESC">최신순</option>
                <option value="likeCount,DESC">즐겨찾기순</option>
              </select>
            </div>
            <div class="col-2 ms-auto">
              <router-link v-if="isAdmin" class="btn btn-primary" :to="{ name: 'FoodWrite'}">식품 추가</router-link>
            </div>
          </div>
          <section class="py-2">
            <div class="container px-4 px-lg-1">
              <div class="row gx-4 gx-lg-2 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                <div class="col mb-5" v-for="food in foodList" :key="food.id">
                  <div class="card-shadow card h-100">
                    <img class="card-img-top" :src="food.food_image_url" alt="..." width="300px" height="150">
                    <div class="card-body p-4">
                      <div class="text-center"
                           style="transform: rotate(0); overflow: hidden;
                           text-overflow: ellipsis;
                           white-space: nowrap;">
                        <a class="food-name" :href="`/foods/${food.id}`">{{ food.food_name }}</a>
                      </div>
                      <div class="text-center mt-2">
                        <button class="btn mt-auto" :class="food.is_like ? 'btn-warning' : 'btn-outline-warning'" @click="favorite(food.is_like, food.id)">
                          <i v-if="!food.is_like" class="bi bi-star"></i>
                          <i v-else class="bi bi-star-fill"></i> {{ food.like_count }}
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
          <div class="d-flex justify-content-center mt-4">

            <!--Pagination-->
            <!-- <Pagination :currentPage="currentPage" :pageDisplayCount="pageDisplayCount" :totalPageCount="totalPageCount" @change="setPage" /> -->

          </div>
        </div>
      </div>

      <!--추천식품-->
      <div class="col-2 mt-4">
        <div class="accordion position-sticky" style="top: 2rem;">
          <div class="accordion-item">
            <div class="accordion-header">
              <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#rightMenu">
                추천 식품
              </button>
            </div>
            <div id="rightMenu" class="accordion-collapse collapse show">
              <div class="accordion-body">
                <ul class="list-group list-group-flush text-center">
                  <li v-for="recFood in recFoodList" :key="recFood.id" class="list-group-item">
                      <img :src=recFood.food_image_url height="100" width="100" alt=""/><br/>
                      <router-link :to="{ name: 'FoodView', params: { food_id: recFood.id } }" class="food-name stretched-link">{{ recFood.food_name }}</router-link>
                  </li>
                </ul>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

e<script>
import { onMounted, ref, watch } from 'vue'
import * as FoodAPI from '@/services/food.js'
// import Pagination from '@compo/common/Pagination.vue'
// import store from '@/store'

export default {
  // components: {
  //   Pagination,
  // },
  setup() {
    const foodList = ref('')  // getFoodList()로 가져온 식품 데이터
    const recFoodList = ref('')
    const viewFoodList = ref('')
    const isAdmin = ref(false)

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

    watch(allergyCheckData.value, () => {
      setParams()
    })

    const getUserAllergy = () => {
      FoodAPI.getUserAllergy()
        .then((res) => {
          const allergyData = new Map(Object.entries(res.data))
          for (let i = 0; i < allergyData.size; i++) {
            allergyCheckData.value[i].selected = allergyData.get(allergyCheckData.value[i].name)
          }
          setParams()
          getFoodList(1)
        })
        .catch((err) => {
          console.errer(err)
        })
    }

    let params = new URLSearchParams()
    const setParams = () => {
      params = new URLSearchParams()
      for (let i = 0; i < allergyCheckData.value.length; i++) {
        params.append(allergyCheckData.value[i].name, allergyCheckData.value[i].selected)
      }
    }

    const searchText = ref('')  // 검색
    const selectedSort = ref('foodId,DESC') // 정렬
    const currentPage = ref(1)
    const pageDisplayCount = ref(10)
    const totalPageCount = ref()

    const getFoodList = (page = currentPage.value) => {
      setParams()
      FoodAPI.getFoodList(page, selectedSort.value, searchText.value, params)
        .then((res) => {
          totalPageCount.value = parseInt(res.headers['x-page-count']) === 0 ? 1 : parseInt(res.headers['x-page-count'])
          if (res.data.length !== 0) {
            foodList.value = res.data
          } else {
            foodList.value = null
          }
        })
        .catch((err) => {
          console.error(err)
        })
    }

    const getRecentFood = () => {
      FoodAPI.getRecentFood()
        .then(res => viewFoodList.value = res.data)
        .catch(err => console.error(err))
    }

    const getRecommendFood = () => {
      FoodAPI.getRecommendFood()
        .then(res => recFoodList.value = res.data)
        .catch(err => console.error(err))
    }

    const setPage = (page) => {
      currentPage.value = page
      getFoodList(page)
    }

    const searchFood = () => {
      currentPage.value = 1
      getFoodList(1)
    }

    const checkPermission = () => {
      isAdmin.value = false
      FoodAPI.isAdmin().then(() => isAdmin.value = true)
      // if (store.state.isLogin) {
      //   if (store.state.isAdmin) {
      //     axiosGet('/api/v1/admin', () => {
      //       isAdmin.value = true
      //     }, () => {
      //       isAdmin.value = false
      //     })
      //   }
      // }
    }

    const favorite = (isLike, foodId) => {
      FoodAPI.favorite(isLike, foodId).then(() => location.reload())
    }

    watch(selectedSort, () => {
      getFoodList(1)
    })

    onMounted(() => {
      getUserAllergy()
      getRecommendFood()
      getRecentFood()
      checkPermission()
    })

    return {
      allergyCheckData,
      getFoodList,
      searchFood,
      foodList,
      searchText,
      currentPage,
      pageDisplayCount,
      totalPageCount,
      setPage,
      getRecommendFood,
      recFoodList,
      viewFoodList,
      isAdmin,
      favorite,
      selectedSort,
    }
  }
}
</script>

<style scoped>
.food-name {
  text-decoration: none;
  color:black;
}
.food-name:hover {
  text-decoration: underline;
  color: blue;
}
.card-shadow:hover {
  box-shadow: 0 .125rem .25rem rgba(0, 0, 0, 0.5);
}
</style>
<template>
  <!-- load fail -->
  <div v-if="loading === -1">
    <NotFound/>
  </div>
  <!-- loading -->
  <div v-else-if="loading === 0">
    <!-- <Loading/> -->
  </div>
  <!-- load success -->
  <div v-else class="row">
    <div class="mt-4">
      <div class="card shadow">
        <div class="card-body">
          <h5 class="card-title" style="display:inline">{{ foodData.food_name }}</h5>
          <button class="btn mt-auto" :class="foodData.is_like ? 'btn-warning' : 'btn-outline-warning'" @click="favorite(foodData.is_like, foodData.id)" style=" float: right;">
            <i v-if="!foodData.is_like" class="bi bi-star"></i>
            <i v-else class="bi bi-star-fill"></i> {{ foodData.like_count }}
          </button>
          <button type="button" class="btn btn-primary" style=" float: right;margin-right: 5px;" @click="moveToFoodListPage">목록</button>
          <hr/>
          <div class="card border-light mb-3">
            <div class="row g-0">
              <div class="col-md-4">
                <img :src="foodData.food_image_url" class="img-fluid rounded-start" alt="...">
              </div>
              <div class="col-md-8">
                <div class="card-body" style="padding-top: 0px;padding-bottom: 0px;">
                  <table class="table">
                    <thead>
                    <tr>
                      <th scope="col" style="width: 150px"></th>
                      <th scope="col" style="width: 500px"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                      <th scope="row">식품보고번호</th>
                      <td>{{ foodData.code }}</td>
                    </tr>
                    <tr>
                      <th scope="row">식품 종류</th>
                      <td>{{ foodData.product_kind }}</td>
                    </tr>
                    <tr>
                      <th scope="row">영양 성분</th>
                      <td>{{ foodData.nutrition }}</td>
                    </tr>
                    <tr>
                      <th scope="row">원재료명</th>
                      <td>{{ foodData.raw_materials }}</td>
                    </tr>
                    <tr>
                      <th scope="row">알레르기 성분</th>
                      <td>{{ foodData.allergy }}</td>
                    </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <router-link v-if="isAdmin" class="btn btn-primary mt-2" :to="{ name: 'FoodWrite', query: { id: foodData.id, editable: true } }">수정</router-link>
              <button v-if="isAdmin" class="btn btn-danger mt-2" @click="foodDelete(foodData.id)">삭제</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="mt-4">
      <div class="card shadow">
        <div class="card-body">
          <h5 class="card-title">추천 상품</h5>
          <hr/>
          <section class="py-2">
            <div class="container px-4 px-lg-1">
              <div class="row gx-4 gx-lg-2 row-cols-2 row-cols-md-3 row-cols-xl-5 justify-content-center">
                <div v-for="recFood in recFoodList" :key="recFood.id" class="col mb-2">
                  <div class="card h-100 card-shadow">
                    <img class="card-img-top" :src=recFood.food_image_url width="250" height="200" alt="...">
                    <div class="card-body p-4">
                      <div class="text-center">
                        <a class="food-name" :href="`/foods/${recFood.id}`">{{ recFood.food_name }}</a>
                      </div>
                      <div class="text-center mt-2">
                        <button class="btn mt-auto" :class="recFood.is_like ? 'btn-warning' : 'btn-outline-warning'" @click="favorite(recFood.is_like, recFood.id)">
                          <i v-if="!recFood.is_like" class="bi bi-star"></i>
                          <i v-else class="bi bi-star-fill"></i> {{ recFood.like_count }}
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRoute, useRouter } from 'vue-router'
import { onMounted, ref } from 'vue'
import * as FoodAPI from '@/services/food.js'
import Swal from 'sweetalert2'
// import store from '@/store'
// import Loading from '@compo/common/Loading'
import NotFound from '@compo/common/ErrorPage'

export default {
  components: {
    // Loading,
    NotFound,
  },
  setup() {
    const route = useRoute()
    const router = useRouter()
    const loading = ref(0)
    const isAdmin = ref(false)
    const foodId = route.params.id
    const recFoodList = ref('')
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

    const foodDelete = (foodId) => {
      Swal.fire({
        title: '식품 삭제',
        text: '정말 식품을 삭제하시겠습니까?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        confirmButtonText: '삭제',
        cancelButtonText: '취소'
      }).then((res) => {
        if (res.isConfirmed) {
          FoodAPI.deleteFood(foodId)
            .then(() => {
              Swal.fire({ title: '삭제 완료!', text: '식품이 삭제되었습니다.', icon: 'success', timer: 1500 })
              router.go(-1)
            })
            .catch(() => {
              Swal.fire({ title: '삭제 실패...', icon: 'error', text: '식품을 삭제할 수 없습니다.', showConfirmButton: false, timer: 1500 })
            })
        }
      })
    }

    const moveToFoodListPage = () => {
      router.go(-1)
    }

    onMounted(() => {
      FoodAPI.getFood(foodId)
        .then((res) => {
          loading.value = 1
          foodData.value = res.data
        })
        .catch((err) => {
          loading.value = -1
          console.error(err)
        })

      FoodAPI.getRecommendFood()
        .then(res => recFoodList.value = res.data)
        .catch(err => console.error(err))

      checkPermission()
    })

    return {
      loading,
      foodId,
      foodData,
      moveToFoodListPage,
      favorite,
      recFoodList,
      checkPermission,
      isAdmin,
      foodDelete,
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
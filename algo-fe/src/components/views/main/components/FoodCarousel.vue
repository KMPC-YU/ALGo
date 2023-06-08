<template>
<!--  <h3 class="font-weight-bold mb-2">식품 BEST 10</h3>-->
<!--  <hr>-->
<!--  <div id="foodCarousel" class="carousel slide">-->
<!--    <div class="carousel-inner">-->
<!--      <div class="carousel-item active">-->
<!--        <div class="row row-cols-1 row-cols-md-4 row-cols-lg-5 g-4">-->
<!--          <div class="col" v-for="(foods, index) in bestFoods[0]" :key="index">-->
<!--            <food-card-->
<!--              :img-src=foods.imgUrl-->
<!--              :img-alt="''"-->
<!--              :name=foods.foodName-->
<!--              :views=foods.views-->
<!--              :likes="foods.likes">-->
<!--            </food-card>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--      <div class="carousel-item">-->
<!--        <div class="row row-cols-1 row-cols-md-5 g-4">-->
<!--          <div class="col" v-for="(foods, index) in bestFoods[1]" :key="index">-->
<!--            <food-card-->
<!--                :img-src=foods.imgUrl-->
<!--                :img-alt="''"-->
<!--                :name=foods.foodName-->
<!--                :views=foods.views-->
<!--                :likes="foods.likes">-->
<!--            </food-card>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--    <button class="carousel-control-prev" type="button" data-bs-target="#foodCarousel" data-bs-slide="prev">-->
<!--      <i class="bi bi-chevron-left text-black"></i>-->
<!--    </button>-->
<!--    <button class="carousel-control-next" type="button" data-bs-target="#foodCarousel" data-bs-slide="next">-->
<!--      <i class="bi bi-chevron-right text-black"></i>-->
<!--    </button>-->
<!--  </div>-->
</template>

<script>
import FoodCard from "@compo/common/FoodCard.vue";
// import useAxios from '@/modules/axios'
import { onMounted, ref } from "vue";

export default {
  components: {
    FoodCard,
  },
  setup() {
    // const { axiosGet } = useAxios()

    onMounted(() => {
      getFoods()
    })

    const bestFoods = ref([])
    const getFoods = () => {
      // axiosGet('http://localhost:3001/bestfoods', (res) => {
      //   bestFoods.value[0] = res.data.slice(0, 5)
      //   bestFoods.value[1] = res.data.slice(5, 10)
      // }, (err) => {
      //   console.error(err)
      // })
    }

    return {
      bestFoods
    }
  }
}
</script>

<style scoped>
.carousel-inner {
  padding: 1em;
}
.carousel-control-prev,
.carousel-control-next {
  background-color: #FFFFFF;
  width: 5vh;
  height: 5vh;
  border-radius: 50%;
  top: 50%;
  transform: translateY(-50%);
  opacity: 100%;
  box-shadow: 2px 6px 8px 0 rgba(22, 22, 26, 0.18);
}
</style>
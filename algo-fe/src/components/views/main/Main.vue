<template>
  <div class="container">
    <div>
      <carousel></carousel>
    </div>
    <div class="mt-5">
      <div class="card">
        <div class="card-body">
          <food-carousel></food-carousel>
        </div>
      </div>
    </div>
    <div class="mt-5">
      <div class="card">
        <div class="card-body">
          <recipe-carousel></recipe-carousel>
        </div>
      </div>
    </div>
<!--    <div class="mt-5">-->
<!--      <category></category>-->
<!--    </div>-->
  </div>
</template>

<script>
import Carousel from './components/MainCarousel.vue'
import FoodCarousel from "./components/FoodCarousel.vue";
import RecipeCarousel from "./components/RecipeCarousel.vue";
import Category from "./components/AllergyCategory.vue";

export default {
  components: {
    Carousel,
    FoodCarousel,
    RecipeCarousel,
    Category,
  }
}
</script>

<style scoped>
.card {
  border-radius: 0;
}
</style>
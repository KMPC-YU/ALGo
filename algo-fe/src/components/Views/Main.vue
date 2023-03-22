<template>
  <div class="wrapper">
    <div class="container">
      <div>
        <carousel></carousel>
      </div>
      <div class="mt-5">
        <div class="card">
          <div class="card-body">
            <food-cards></food-cards>
          </div>
        </div>
      </div>
      <div class="mt-5">
        <div class="card">
          <div class="card-body">
            <recipe-card></recipe-card>
          </div>
        </div>
      </div>
<!--      <div class="mt-5">-->
<!--        <category></category>-->
<!--      </div>-->
    </div>
  </div>
</template>

<script>
import Carousel from "@compo/MainComponents/Carousel.vue";
import FoodCards from "@compo/MainComponents/FoodCard.vue";
import RecipeCard from "@compo/MainComponents/RecipeCard.vue";
import Category from "@compo/MainComponents/Category.vue";

export default {
  components: {
    Carousel,
    FoodCards,
    RecipeCard,
    Category,
  }
}
</script>

<style scoped>
.card {
  border-radius: 0;
}
</style>